package com.nmh.commerce.learning

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.assertj.core.api.BDDAssertions.then
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.Connection
import javax.sql.DataSource
import kotlin.system.measureNanoTime

@Testcontainers
class MySQLIndexPerformanceTest {

    @Container
    private val mysql = MySQLContainer("mysql:8.0")
        .withDatabaseName("test")
        .withUsername("root")
        .withPassword("root")

    private lateinit var dataSource: DataSource

    @BeforeEach
    fun setUp() {
        dataSource = HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = mysql.jdbcUrl
                username = mysql.username
                password = mysql.password
                maximumPoolSize = 5
            },
        )

        dataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("DROP TABLE IF EXISTS user")
                stmt.execute(
                    """
                    CREATE TABLE user (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        email VARCHAR(255),
                        age INT,
                        created_at DATETIME
                    )
                    """.trimIndent(),
                )
            }
        }
    }

    @Test
    fun `인덱스가 없을 때와 있을 때 쿼리 속도 비교`() {
        dataSource.connection.use { conn ->
            val insertSQL = "INSERT INTO user (name, email, age, created_at) VALUES (?, ?, ?, NOW())"
            conn.prepareStatement(insertSQL).use { ps ->
                for (i in 1..1_000) {
                    ps.setString(1, "User$i")
                    ps.setString(2, "user$i@example.com")
                    ps.setInt(3, i % 100)
                    ps.addBatch()
                    if (i % 100 == 0) ps.executeBatch()
                }
                ps.executeBatch()
            }

            val noIndexTimeNs = measureQueryTimeNano(conn, "SELECT * FROM user WHERE email = 'user500@example.com'")
            println("No index time: $noIndexTimeNs ns (${noIndexTimeNs / 1_000_000.0} ms)")

            conn.createStatement().execute("CREATE INDEX idx_user_email ON user (email)")

            val withIndexTimeNs = measureQueryTimeNano(conn, "SELECT * FROM user WHERE email = 'user500@example.com'")
            println("With index time: $withIndexTimeNs ns (${withIndexTimeNs / 1_000_000.0} ms)")

            then(withIndexTimeNs).isLessThan(noIndexTimeNs)
        }
    }

    @Test
    fun `복합 인덱스를 사용할 때 인덱스 순서에 맞게 사용해야한다`() {
        dataSource.connection.use { conn ->
            conn.createStatement().use { stmt ->
                stmt.execute("DROP TABLE IF EXISTS user")
                stmt.execute(
                    """
                CREATE TABLE user (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255),
                    age INT,
                    created_at DATETIME
                )
                    """.trimIndent(),
                )
            }

            val insertSQL = "INSERT INTO user (name, age, created_at) VALUES (?, ?, NOW())"
            conn.prepareStatement(insertSQL).use { ps ->
                for (i in 1..1000) {
                    ps.setString(1, "User${i % 100}") // name은 100종류
                    ps.setInt(2, i % 50) // age는 50종류
                    ps.addBatch()
                    if (i % 100 == 0) ps.executeBatch()
                }
                ps.executeBatch()
            }

            // 인덱스가 없는 상태에서 쿼리 성능 측정
            val noIndexTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE name = 'User10' AND age = 30")
            println("No index time: $noIndexTime ns (${noIndexTime / 1_000_000.0} ms)")
            printExplain(conn, "SELECT * FROM user WHERE name = 'User10' AND age = 30")

            // 복합 인덱스를 (name, age) 순서로 설정
            conn.createStatement().execute("CREATE INDEX idx_user_name_age ON user (name, age)")

            // 잘못된 순서의 WHERE 조건 (age 먼저)
            val wrongOrderTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE age = 30 AND name = 'User10'")
            println("Wrong order (age first) time: $wrongOrderTime ns (${wrongOrderTime / 1_000_000.0} ms)")

            // 올바른 순서의 WHERE 조건 (name 먼저)
            val correctOrderTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE name = 'User10' AND age = 30")
            println("Correct order (name first) time: $correctOrderTime ns (${correctOrderTime / 1_000_000.0} ms)")

            printExplain(conn, "SELECT * FROM user WHERE age = 30 AND name = 'User10'")
            printExplain(conn, "SELECT * FROM user WHERE name = 'User10' AND age = 30")

            then(correctOrderTime).isLessThan(wrongOrderTime)
            /*
            항상 순서가 잘못되어있다고 해서 인덱스를 사용하지 않는 것은 아니다.
            하지만 인덱스의 순서에 맞게 쿼리를 작성하는 것이 성능을 최적화하는 데 도움이 된다.

            실제로 explain을 확인해보면 wrongOrderTime 쿼리는 인덱스를 사용하는 것으로 나온다
            인덱스를 탈지 안탈지 여부는 내가 쿼리를 잘 작성하는 것과 연관은 없다.
            물론 인덱스를 사용하도록 유도할 수 있지만 실제로 인덱스를 사용할지 사용하지 않을 지 선택하는 것은 MySQLd의 옵티마이저가 결정한다.
             */
        }
    }

    @Test
    fun `Range Scan 테스트`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            val noIndexTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE age BETWEEN 20 AND 30")
            println("No index range scan time: $noIndexTime ns (${noIndexTime / 1_000_000.0} ms)")
            printExplain(conn, "SELECT * FROM user WHERE age BETWEEN 20 AND 30")

            conn.createStatement().execute("CREATE INDEX idx_user_age ON user (age)")

            val time = measureQueryTimeNano(conn, "SELECT * FROM user WHERE age BETWEEN 20 AND 30")
            println("Range scan query time: $time ns (${time / 1_000_000.0} ms)")
            printExplain(conn, "SELECT * FROM user WHERE age BETWEEN 20 AND 30")

            then(time).isLessThan(noIndexTime)
        }
    }

    @Test
    fun `LIKE 절을 인덱스를 사용하기 위해서는 %keyword%로 검색 할 수 없다`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name ON user (name)")

            val prefixLikeTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE name LIKE 'User1%'")
            val infixLikeTime = measureQueryTimeNano(conn, "SELECT * FROM user WHERE name LIKE '%ser1'")

            println("Prefix LIKE index query time: $prefixLikeTime ns (${prefixLikeTime / 1_000_000.0} ms)")
            println("Infix LIKE no index query time: $infixLikeTime ns (${infixLikeTime / 1_000_000.0} ms)")

            printExplain(conn, "SELECT * FROM user WHERE name LIKE 'User1%'")
            printExplain(conn, "SELECT * FROM user WHERE name LIKE '%ser1'")

            then(infixLikeTime).isLessThan(prefixLikeTime)
        }
    }

    @Test
    fun `GROUP BY와 커버링 인덱스 가능 케이스`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name_age_created_at ON user (name, age, created_at)")

            // 커버링 인덱스 사용 가능: SELECT, WHERE, GROUP BY가 모두 인덱스 컬럼만 사용하고, 순서도 인덱스 컬럼 순서와 일치.
            val query = "SELECT name, age, created_at FROM user WHERE name = 'User10' GROUP BY name, age, created_at"
            measureQueryWithExplain(conn, query)
        }
    }

    @Test
    fun `GROUP BY에서 인덱스 컬럼 일부만 사용하여 커버링이 가능한 경우`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name_age_created_at ON user (name, age, created_at)")

            // 커버링 인덱스 가능:
            // SELECT와 GROUP BY가 (name, age)까지만 사용하지만, 인덱스 앞부분만 사용해도 커버링 인덱스 성립.
            // created_at은 SELECT/GROUP BY에 없어도 영향 없음 (필요한 컬럼만 인덱스에 있으면 커버링 가능).
            val query = "SELECT name, age FROM user WHERE name = 'User10' GROUP BY name, age"
            measureQueryWithExplain(conn, query)
        }
    }

    @Test
    fun `HAVING에서 인덱스 외 컬럼 사용 시 커버링 불가능`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name_age_created_at ON user (name, age, created_at)")

            // 커버링 인덱스 사용 가능:
            // COUNT(*)는 row 개수만 세는 것이므로 인덱스 컬럼만으로 계산 가능 → 커버링 인덱스 O
            //
            // 참고: 인덱스 외 컬럼(SUM(score) 등)을 HAVING에서 사용하는 경우 커버링 인덱스가 불가능하고 테이블 row 접근이 필요하다.
            val query = "SELECT name, age, created_at FROM user WHERE name = 'User10' GROUP BY name, age, created_at HAVING COUNT(*) > 1"
            measureQueryWithExplain(conn, query)
        }
    }

    @Test
    fun `ORDER BY가 인덱스 순서와 일치할 때 커버링 가능`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name_age_created_at ON user (name, age, created_at)")

            // 커버링 인덱스 + ORDER BY 순서 일치:
            // name → age → created_at 순서로 인덱스와 정렬 조건이 일치하므로 filesort 없이 인덱스 스캔만으로 정렬 가능.
            val query = "SELECT name, age, created_at FROM user WHERE name = 'User10' ORDER BY name, age, created_at"
            measureQueryWithExplain(conn, query)
        }
    }

    @Test
    fun `ORDER BY가 인덱스 순서와 다를 때 커버링 불가능`() {
        dataSource.connection.use { conn ->
            prepareTestData(conn)
            conn.createStatement().execute("CREATE INDEX idx_user_name_age_created_at ON user (name, age, created_at)")

            // 커버링 인덱스는 가능하나, ORDER BY 순서가 다르면 filesort 발생할 수 있음.
            // 단, name이 WHERE로 고정되면 사실상 ORDER BY age만 남아서 인덱스 정렬 순서를 그대로 활용할 수 있다.
            val query = "SELECT name, age, created_at FROM user WHERE name = 'User10' ORDER BY age, name"
            measureQueryWithExplain(conn, query)

            /*
             * 주의: name이 WHERE로 고정되면 그룹 안에서는 age 순서가 유지되므로
             * 인덱스 정렬 순서와 실질적으로 충돌하지 않고 정렬이 유지될 수 있다.
             */
        }
    }

    private fun measureQueryTimeNano(conn: Connection, query: String): Long = measureNanoTime {
        conn.prepareStatement(query).use { ps ->
            ps.executeQuery().use { rs ->
                while (rs.next()) {
                    /* Do nothing */
                }
            }
        }
    }

    private fun printExplain(conn: Connection, query: String) {
        println("EXPLAIN for query: $query")
        conn.prepareStatement("EXPLAIN $query").use { ps ->
            ps.executeQuery().use { rs ->
                val meta = rs.metaData
                val columnCount = meta.columnCount

                while (rs.next()) {
                    val row = (1..columnCount)
                        .joinToString(", ") { idx -> "${meta.getColumnName(idx)}: ${rs.getString(idx)}" }
                    println(row)
                }
            }
        }
    }

    private fun prepareTestData(conn: Connection) {
        val insertSQL = "INSERT INTO user (name, email, age, created_at) VALUES (?, ?, ?, NOW())"
        conn.prepareStatement(insertSQL).use { ps ->
            for (i in 1..1_000) {
                ps.setString(1, "User${i % 100}")
                ps.setString(2, "user$i@example.com")
                ps.setInt(3, i % 50)
                ps.addBatch()
                if (i % 100 == 0) ps.executeBatch()
            }
            ps.executeBatch()
        }
    }

    private fun measureQueryWithExplain(conn: Connection, query: String) {
        val time = measureNanoTime {
            conn.prepareStatement(query).use { ps ->
                ps.executeQuery().use { rs ->
                    while (rs.next()) {
                        /* Do nothing */
                    }
                }
            }
        }
        println("Query time: $time ns (${time / 1_000_000.0} ms)")
        printExplain(conn, query)
    }
}

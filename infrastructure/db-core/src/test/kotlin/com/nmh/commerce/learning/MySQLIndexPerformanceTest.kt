package com.nmh.commerce.learning

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.assertj.core.api.BDDAssertions
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

            BDDAssertions.then(withIndexTimeNs).isLessThan(noIndexTimeNs)
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

            BDDAssertions.then(correctOrderTime).isLessThan(wrongOrderTime)
            /*
            항상 순서가 잘못되어있다고 해서 인덱스를 사용하지 않는 것은 아니다.
            하지만 인덱스의 순서에 맞게 쿼리를 작성하는 것이 성능을 최적화하는 데 도움이 된다.

            실제로 explain을 확인해보면 wrongOrderTime 쿼리는 인덱스를 사용하는 것으로 나온다
            인덱스를 탈지 안탈지 여부는 내가 쿼리를 잘 작성하는 것과 연관은 없다.
            물론 인덱스를 사용하도록 유도할 수 있지만 실제로 인덱스를 사용할지 사용하지 않을 지 선택하는 것은 MySQLd의 옵티마이저가 결정한다.
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
}

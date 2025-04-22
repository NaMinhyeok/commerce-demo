package com.nmh.commerce.learning

import org.junit.jupiter.api.Test
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.sql.DriverManager

@Testcontainers
class TestContainerStartUpTest {

    @Container
    private val mysql = MySQLContainer((DockerImageName.parse("mysql:8.0")))
        .withDatabaseName("test")
        .withUsername("root")
        .withPassword("root")

    @Test
    fun `MySQL 실행 테스트`() {
        mysql.start()

        val jdbcUrl = mysql.jdbcUrl
        val username = mysql.username
        val password = mysql.password

        println("JDBC URL: $jdbcUrl")
        println("Username: $username")
        println("Password: $password")

        DriverManager.getConnection(jdbcUrl, username, password).use { connection ->
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT 1")
            if (resultSet.next()) {
                println("MySQL is running!")
            } else {
                println("MySQL is not running!")
            }
        }
    }
}

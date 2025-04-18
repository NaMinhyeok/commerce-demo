import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@ConfigurationPropertiesScan
@SpringBootApplication
object CoreApplicationTestApplication {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(CoreApplicationTestApplication::class.java, *args)
    }
}

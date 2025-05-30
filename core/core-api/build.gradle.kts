import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks.withType<BootJar> {
    enabled = true
}

tasks.withType<Jar> {
    enabled = false
}

dependencies {
    implementation(project(":common"))
    implementation(project(":infrastructure:db-core"))
    implementation(project(":core:core-application"))
    implementation(project(":core:core-domain"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("jakarta.servlet:jakarta.servlet-api")
    compileOnly("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.restdocs:spring-restdocs-mockmvc")
    api("org.springframework.restdocs:spring-restdocs-restassured")
    api("io.rest-assured:spring-mock-mvc")
}

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

    implementation("org.springframework.boot:spring-boot-starter-web")
}
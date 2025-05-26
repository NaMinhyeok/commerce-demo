dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":common"))
    implementation("org.ehcache:ehcache:3.10.8")
    implementation("javax.cache:cache-api:1.1.1")
    implementation("org.springframework.boot:spring-boot-starter-cache")
}

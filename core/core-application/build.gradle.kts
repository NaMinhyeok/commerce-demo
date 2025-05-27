dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation(project(":core:core-domain"))
    implementation(project(":common"))
//    implementation(project(":external:exchange"))
    implementation(project(":infrastructure:cache"))

    testImplementation(project(":infrastructure:db-core"))
}

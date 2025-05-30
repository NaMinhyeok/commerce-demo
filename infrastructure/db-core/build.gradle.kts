plugins {
    kotlin("plugin.allopen")
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":common"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:junit-jupiter")
    implementation("mysql:mysql-connector-java:8.0.33")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

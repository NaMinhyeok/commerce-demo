import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.25" apply false
    id("com.diffplug.spotless") version "7.0.3"
}

subprojects {

    apply {
        plugin("java")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("com.diffplug.spotless")
    }

    group = "com.nmh.commerce"
    version = "0.0.1-SNAPSHOT"

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    repositories {
        mavenCentral()
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2024.0.1")
        }
    }

    dependencies {
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<BootJar> {
        enabled = false
    }

    tasks.withType<Jar> {
        enabled = true
    }

    tasks.withType<KotlinCompile> {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.set(
                listOf(
                    "-Xjsr305=strict",
                ),
            )
        }
    }

    spotless {
        kotlin {
            ktlint("1.5.0")
                .setEditorConfigPath(file("${rootProject.projectDir}/.editorconfig"))
            target("**/*.kt")
            targetExclude("**/build/**/*.kt")
        }

        kotlinGradle {
            ktlint()
            target("**/*.kts")
        }
    }

}

tasks.register<Copy>("installGitHooks") {
    description = "공유된 git hooks를 .git/hooks/로 복사"
    group = "git"

    from("$rootDir/.githooks/")
    into("$rootDir/.git/hooks/")

    filePermissions {
        user {
            execute = true
            read = true
            write = true
        }
        group.execute = true
        other.execute = true
    }
}


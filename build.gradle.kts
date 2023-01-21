import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinPluginVersion = "1.7.22"

    id("org.springframework.boot") version "3.0.1" apply false
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version kotlinPluginVersion
    kotlin("plugin.spring") version kotlinPluginVersion
    kotlin("plugin.jpa") version kotlinPluginVersion
    kotlin("kapt") version kotlinPluginVersion
}

java.sourceCompatibility = JavaVersion.VERSION_17

allprojects {
    group = "com.soon83"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-kapt")

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
        }
    }

    dependencies {
        // jwt token
        implementation("com.auth0:java-jwt:4.2.1")

        // kotlin logging
        implementation("io.github.microutils:kotlin-logging:3.0.4")

        // kotlin
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // test
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application

    id("org.springframework.boot") version "2.7.2"
    id("io.spring.dependency-management") version "1.0.12.RELEASE"
    kotlin("plugin.spring") version "1.7.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":checkers"))
    implementation("org.jetbrains.kotlin:kotlin-bom:1.7.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.4")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

}
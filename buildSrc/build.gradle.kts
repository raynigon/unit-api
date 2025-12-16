plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

dependencies {
    implementation("io.spring.gradle:dependency-management-plugin:1.1.7")
    implementation("com.github.spotbugs.snom:spotbugs-gradle-plugin:6.0.1")
    implementation("org.owasp:dependency-check-gradle:10.0.3")

    implementation("org.springframework.boot:spring-boot-gradle-plugin:4.0.0")
}
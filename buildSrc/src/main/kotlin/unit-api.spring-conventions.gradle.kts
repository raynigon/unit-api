import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("unit-api.java-conventions")

    id("org.springframework.boot")
}

tasks.named<BootJar>("bootJar") {
    enabled = false
}

dependencies {
    compileOnly("org.springframework.boot:spring-boot-starter")
    compileOnly("org.springframework:spring-context-support")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework:spring-context-support")
    testImplementation("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.spockframework:spock-spring:2.4-groovy-5.0")
}
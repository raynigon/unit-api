import org.gradle.kotlin.dsl.withType
import org.owasp.dependencycheck.reporting.ReportGenerator.Format

plugins {
    java
    `java-library`
    id("io.spring.dependency-management")

    checkstyle
    pmd
    id("com.github.spotbugs")
    id("org.owasp.dependencycheck")

    id("unit-api.version-class")
    id("unit-api.javadoc")
    id("unit-api.test")
    id("unit-api.publishing")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

tasks.withType<Checkstyle>().configureEach {
    configFile = File(rootProject.projectDir, "gradle/config/checkstyle.xml")
    reports {
        xml.required = true
        html.required = true
    }
}

pmd {
    isConsoleOutput = true
    isIgnoreFailures = true
    toolVersion = "6.41.0"
    rulesMinimumPriority = 3
}

spotbugs {
    ignoreFailures = true
}

dependencyCheck {
    autoUpdate = false
    skipTestGroups = true
    suppressionFile = "${parent!!.projectDir}/gradle/config/dependency-check-suppression.xml"
    formats = listOf(Format.JSON.toString(), Format.HTML.toString())

    analyzers.nodeEnabled = false
    analyzers.nuspecEnabled = false
    analyzers.nugetconfEnabled = false
    analyzers.assemblyEnabled = false
    analyzers.retirejs.enabled = false
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    api("javax.measure:unit-api:2.2")
    api("org.apache.commons:commons-lang3:3.14.0")
    api("org.slf4j:slf4j-api:2.0.13")
    api("org.jetbrains:annotations:24.1.0")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}
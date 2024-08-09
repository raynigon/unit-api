plugins {
    java
    groovy
    jacoco
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = true
        html.required = true
        csv.required = false
    }
}

dependencies {
    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testImplementation("org.apache.groovy:groovy:4.0.21")
    testImplementation("org.spockframework:spock-core:2.4-M4-groovy-4.0")
    testRuntimeOnly("net.bytebuddy:byte-buddy:1.14.13")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
}
import groovy.util.Node
import groovy.util.NodeList

plugins {
    `java-library`
    `maven-publish`
    signing
}

java {
    withJavadocJar()
    withSourcesJar()
}

signing {
    val signingKey = findProperty("signingKey").toString()
    val signingPassword = findProperty("signingPassword").toString()
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

publishing {
    repositories {
        maven {
            name = "OSSRH" // OSS Repository Hosting
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME") ?: null
                password = System.getenv("OSSRH_PASSWORD") ?: null
            }

        }
    }
    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name
            from(components["java"])

            pom {
                name = project.name
                description = "The ${project.name} is a part of the unit-api"
                url = "https://unit-api.raynigon.com/"
                issueManagement {
                    system = "GitHub"
                    url = "https://github.com/raynigon/unit-api/issues"
                }
                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://opensource.org/licenses/Apache-2.0"
                    }
                }
                scm {
                    url = "https://github.com/raynigon/unit-api/"
                    connection = "scm:git:git://github.com/raynigon/unit-api.git"
                    developerConnection = "scm:git:ssh://git@github.com/raynigon/unit-api.git"
                }
                developers {
                    developer {
                        id = "raynigon"
                        name = "Simon Schneider"
                        email = "opensource@raynigon.de"
                    }
                }
            }
            pom.withXml {
                val rootNode = asNode()
                rootNode.remove((rootNode.get("dependencyManagement") as NodeList)[0] as Node)
            }
        }
    }
}
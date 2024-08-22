import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version "2.0.20"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1"

    id("unit-api.java-conventions")

    // Auto Update Versions
    id("se.patrikerdes.use-latest-versions")
    id("com.github.ben-manes.versions")
}

dependencies {
    implementation(project(":unit-api-core"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
}

kotlin {
    jvmToolchain(17)
}

/**
 * Checks if the given version is stable or not.
 * A version is stable, if it is semver conform,
 * or contains specific Keywords like 'RELEASE', 'FINAL' or 'GA'
 *
 * @param version     the version to check for
 * @return true if the version is stable, false if not
 * */
fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    // Example 1: reject all non stable versions
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

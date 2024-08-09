import java.time.OffsetDateTime
import java.util.regex.Pattern

plugins {
    java
}

val generateVersionClass = tasks.register("generateVersionClass") {
    val outputDir = layout.buildDirectory.dir("generated/sources/version/java/main")
    outputs.dir(outputDir)
    outputs.cacheIf { false } // Always regenerate for current time
    mustRunAfter(tasks.named("clean"))
    group = "build"

    doLast {
        // change package name
        val packageName = project.group.toString().replace("-", "_") + "." + project.name.replace("-", "_")
        val targetDirectory = outputDir.get().dir(packageName.replace(".", "/")).asFile
        targetDirectory.mkdirs()
        var version = project.version.toString()
        val versionPattern = Pattern.compile("^[0-9]+\\.[0-9]+\\.[0-9]+(?>-SNAPSHOT)?\$")
        if (!versionPattern.matcher(version).find()) {
            version = "0.0.1-SNAPSHOT"
        }
        File(targetDirectory, "BuildVersion.java").writeText("""package ${packageName};
import java.time.OffsetDateTime;

/**
 * The BuildVersion class allows easy access to artifact id, group id, artifact version and build time.
 * The version is also available as integers in the attributes MAJOR_VERSION, MINOR_VERSION and PATCH_VERSION.
 */
public class BuildVersion {
    /**
     * The artifact id of this artifact
     */
    public static final String ARTIFACT_ID = "${project.name}";

    /**
     * The group id of this artifact
     */
    public static final String GROUP_ID = "${project.group}";


    /**
     * The version of this artifact
     */
    public static final String VERSION = "${version}";

    /**
     * The major part for the version of this artifact as an integer
     */
    public static final int MAJOR_VERSION = ${version.split(".")[0]};

    /**
     * The minor part for the version of this artifact as an integer
     */
    public static final int MINOR_VERSION = ${version.split(".")[1]};

    /**
     * The patch part for the version of this artifact as an integer
     */
    public static final int PATCH_VERSION = ${version.split(".")[2].split('-')[0]};

    /**
     * Indicates if this artifact is a snapshot version
     */
    public static final boolean SNAPSHOT = ${version.contains("-")};

    /**
     * The date time when this artifact was created
     */
    public static final OffsetDateTime BUILD_DATE = OffsetDateTime.parse("${OffsetDateTime.now()}");
}
""")
    }
}

sourceSets {
    main {
        java {
            srcDir(generateVersionClass)
        }
    }
}

tasks.named("compileJava") {
    dependsOn(generateVersionClass)
}

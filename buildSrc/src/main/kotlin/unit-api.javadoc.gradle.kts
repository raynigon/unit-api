plugins {
    java
}

tasks.javadoc {
//    options.addBooleanOption('html5', true)
}

tasks.register<Copy>("globalJavadoc") {
    dependsOn(tasks.javadoc)
    group = "documentation"
    val branchName = (System.getenv("GITHUB_REF") ?: "refs/heads/master").split("/")[2]
    from(layout.buildDirectory.dir("docs/javadoc"))
    into(parent!!.layout.buildDirectory.dir("javadoc/$branchName/${project.name}"))
}

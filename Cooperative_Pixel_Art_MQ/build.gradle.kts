
plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.rabbitmq:amqp-client:5.17.0")
    implementation("org.apache.commons:commons-lang3:3.5")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.project.Main"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

}





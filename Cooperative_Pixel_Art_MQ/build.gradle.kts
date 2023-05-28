plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation ("com.rabbitmq:amqp-client:5.17.0")
    implementation("org.apache.commons:commons-lang3:3.5")
}

tasks.test {
    useJUnitPlatform()
}
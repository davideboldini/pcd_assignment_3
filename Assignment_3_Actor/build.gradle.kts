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

    // This dependency is used by the application.
    implementation ("com.google.guava:guava:31.1-jre")

    implementation("io.vertx:vertx-core:4.4.1")
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("io.vertx:vertx-web-client:4.4.1")
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6")
    /* module 3.1 - actors - akka*/
    implementation (platform("com.typesafe.akka:akka-bom_2.13:2.8.2"))
    implementation ("com.typesafe.akka:akka-actor-typed_2.13")
    testImplementation ("com.typesafe.akka:akka-actor-testkit-typed_2.13")

    /* module 4.2 */
    implementation ("com.rabbitmq:amqp-client:5.17.0")

    /* logging framework SLF4J */
    implementation ("org.slf4j:slf4j-api:2.0.7")
    // testImplementation 'org.slf4j:slf4j-reload4j:2.0.7'
    implementation ("org.slf4j:slf4j-simple:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}

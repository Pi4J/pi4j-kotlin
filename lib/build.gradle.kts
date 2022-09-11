plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.pi4j"
version = "0.2"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.pi4j:pi4j-core:2.2.0")
    testImplementation("com.pi4j:pi4j-core:2.2.0")
    compileOnly("com.pi4j:pi4j-plugin-mock:2.2.0")
    testImplementation("com.pi4j:pi4j-plugin-mock:2.2.0")
    compileOnly("org.slf4j:slf4j-api:1.7.32")
    testImplementation("org.slf4j:slf4j-api:1.7.32")
    compileOnly("org.slf4j:slf4j-simple:1.7.32")
    testImplementation("org.slf4j:slf4j-simple:1.7.32")
    testImplementation(kotlin("test"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
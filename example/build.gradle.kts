plugins {
    java
    kotlin("jvm") version "1.7.10"
    application
}

group = "com.pi4j"
version = "0.2"

application {
    mainClass.set("MinimalExampleKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
    implementation("com.pi4j:pi4j-core:2.6.0")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:2.6.0")
    implementation("com.pi4j:pi4j-plugin-pigpio:2.6.0")
    implementation("com.pi4j:pi4j-plugin-linuxfs:2.6.0")
    implementation("com.pi4j:pi4j-plugin-mock:2.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("org.slf4j:slf4j-simple:2.0.12")
    testImplementation(kotlin("test"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
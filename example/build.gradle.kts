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
    val pi4jVersion: String by rootProject.extra
    val slf4jVersion: String by rootProject.extra
    val kotlinCoroutinesVersion: String by rootProject.extra
    implementation(project(":lib"))
    implementation("com.pi4j:pi4j-core:$pi4jVersion")
    implementation("com.pi4j:pi4j-plugin-raspberrypi:$pi4jVersion")
    implementation("com.pi4j:pi4j-plugin-pigpio:$pi4jVersion")
    implementation("com.pi4j:pi4j-plugin-gpiod:$pi4jVersion")
    implementation("com.pi4j:pi4j-plugin-linuxfs:$pi4jVersion")
    implementation("com.pi4j:pi4j-plugin-mock:$pi4jVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    testImplementation(kotlin("test"))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
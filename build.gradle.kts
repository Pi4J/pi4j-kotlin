import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.7.10"
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

// Set a property
extra["pi4jVersion"] = "2.6.0"
extra["slf4jVersion"] = "2.0.12"
extra["kotlinCoroutinesVersion"] = "1.6.4"

group = "com.pi4j"
version = "2.6.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

nexusPublishing {
    repositories {
        sonatype()
    }
}
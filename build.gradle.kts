plugins {
    java
    kotlin("jvm") version "2.3.0"
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

// Set a property
extra["pi4jVersion"] = "4.0.0"
extra["slf4jVersion"] = "2.0.16"
extra["kotlinCoroutinesVersion"] = "1.10.1"

group = "com.pi4j"
version = "4.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(25)
}

nexusPublishing {
    repositories {
        sonatype()
    }
}

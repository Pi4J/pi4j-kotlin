plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
    id("io.github.sgtsilvio.gradle.maven-central-publishing") version "0.4.1"
    id("io.github.sgtsilvio.gradle.metadata") version "0.6.0"
    signing
}
val libName = "pi4j-ktx"
val pi4jVersion: String by rootProject.extra

group = "com.pi4j"
version = pi4jVersion

repositories {
    mavenCentral()
}

dependencies {
    val slf4jVersion: String by rootProject.extra
    val kotlinCoroutinesVersion: String by rootProject.extra
    compileOnly("com.pi4j:pi4j-core:$pi4jVersion")
    testImplementation("com.pi4j:pi4j-core:$pi4jVersion")
    compileOnly("com.pi4j:pi4j-plugin-mock:$pi4jVersion")
    testImplementation("com.pi4j:pi4j-plugin-mock:$pi4jVersion")
    compileOnly("org.slf4j:slf4j-api:$slf4jVersion")
    testImplementation("org.slf4j:slf4j-api:$slf4jVersion")
    compileOnly("org.slf4j:slf4j-simple:$slf4jVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(25)
}

metadata {
    readableName = "Pi4J-Kotlin"
    description = "Kotlin DSL for Pi4J V2"
    license {
        apache2()
    }
    developers {
        register("mhashim6") {
            fullName = "Muhammad Hashim"
            email = "msg@mhashim6.me"
        }
    }
    github {
        org = "Pi4J"
        repo = "pi4j-kotlin"
    }
}

publishing {
    publications {
        register<MavenPublication>("main") {
            groupId = "com.pi4j"
            artifactId = libName
            from(components["java"])
            pom {
                organization {
                    name.set("Pi4J")
                    url.set("https://pi4j.com")
                }
                developers {
                    developer {
                        id.set("mhashim6")
                        name.set("Muhammad Hashim")
                        email.set("msg@mhashim6.me")
                        organization.set("mhashim6")
                        organizationUrl.set("https://mhashim6.me")
                        properties.run {
                            put("blog", "https://blog.mhashim6.me")
                            put("linkedin", "https://linkedin.com/in/mhashim6")
                        }
                    }
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["main"])
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Name" to "Pi4J-Kotlin",
            "Organisation" to "Pi4J",
            "URL" to "https://github.com/Pi4J/pi4j-kotlin",
            "Author" to "mhashim6",
            "Author-Website" to "https://mhashim6.me"
        )
    }
}

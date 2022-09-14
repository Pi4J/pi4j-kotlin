plugins {
    kotlin("jvm") version "1.7.10"
    `java-library`
    `maven-publish`
}
val libVersion = "0.2"
val libName = "ktx"

group = "com.pi4j"
version = libVersion

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

publishing {
    repositories {
        maven {
            name = "Local"
            url = uri("file://${buildDir}/local-repository")
        }
    }
    publications {
        create<MavenPublication>(libName) {
            groupId = "com.pi4j"
            artifactId = libName
            version = libVersion
            from(components["java"])
            pom {
                version = libVersion
                artifactId = libName
                name.set("Pi4J-Kotlin")
                description.set("Kotlin DSL for Pi4J V2")
                url.set("https://github.com/Pi4J/pi4j-kotlin")
                organization {
                    name.set("Pi4J")
                    url.set("https://pi4j.com")
                }
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                issueManagement {
                    url.set("https://github.com/Pi4J/pi4j-kotlin/issues")
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

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
    `maven-publish`
    signing
}
val libName = "pi4j-ktx-serial"

group = "com.pi4j"

repositories {
    mavenCentral()
}

dependencies {
    api(project(":lib"))
    implementation("com.fazecast:jSerialComm:2.11.0")
    val pi4jVersion: String by rootProject.extra
    val slf4jVersion: String by rootProject.extra
    compileOnly("com.pi4j:pi4j-core:$pi4jVersion")
    compileOnly("org.slf4j:slf4j-api:$slf4jVersion")
    testImplementation("com.pi4j:pi4j-core:$pi4jVersion")
    testImplementation("org.slf4j:slf4j-api:$slf4jVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
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

signing {
    if (hasProperty("signingPassphrase")) {
        val signingKey: String? by project
        val signingPassphrase: String? by project
        useInMemoryPgpKeys(signingKey, signingPassphrase)
        sign(publishing.publications)
    }
}

publishing {
    repositories {
        mavenLocal {
            name = "Local"
            url = uri("file://${layout.buildDirectory.get().asFile}/local-repository")
        }
    }
    publications {
        val pi4jVersion: String by rootProject.extra
        create<MavenPublication>(libName) {
            groupId = "com.pi4j"
            artifactId = libName
            version = pi4jVersion
            from(components["java"])
            pom {
                version = pi4jVersion
                artifactId = libName
                name.set("pi4j-ktx-serial")
                description.set("Kotlin DSL for serial communication via jSerialComm")
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
                scm {
                    connection.set("scm:git:git://github.com/Pi4J/pi4j-kotlin.git")
                    developerConnection.set("scm:git:ssh://github.com:Pi4J:pi4j-kotlin.git")
                    url.set("https://github.com/Pi4J/pi4j-kotlin/tree/master")
                }
            }
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes(
            "Name" to "Pi4J-Kotlin-Serial",
            "Organisation" to "Pi4J",
            "URL" to "https://github.com/Pi4J/pi4j-kotlin",
            "Author" to "mhashim6",
            "Author-Website" to "https://mhashim6.me"
        )
    }
}

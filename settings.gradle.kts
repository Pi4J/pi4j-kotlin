plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

rootProject.name = "Pi4J-Kotlin"
include("lib")
include("lib-serial")
include("example")

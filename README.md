# Pi4J - Kotlin

Kotlin Interface & DSL for [Pi4J V2](https://github.com/Pi4J/pi4j-v2)  
For Pi4J V1 Kotlin Bindings, check [Pi4K](https://github.com/mhashim6/Pi4K)

[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.pi4j/pi4j-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.pi4j/pi4j-ktx)
[![License](https://img.shields.io/github/license/pi4j/pi4j-v2)](http://www.apache.org/licenses/LICENSE-2.0)
![GitHub Actions build state](https://github.com/pi4j/pi4j-kotlin/workflows/Build%20pi4j-kotlin/badge.svg)

## Documentation
Full documentation can be found on the [website](https://pi4j.com/kotlin/kotlin-api-docs/)

## Example

This is a minimal working example, make sure
to [check it out](https://github.com/Pi4J/pi4j-kotlin/blob/master/example/src/main/kotlin/MinimalExample.kt) for full
introduction and comments.

``` kotlin
const val PIN_BUTTON = 24 // PIN 18 = BCM 24
const val PIN_LED = 22 // PIN 15 = BCM 22
var pressCount = 0

console {
  title("<-- The Pi4J Project -->", "Minimal Example project")
  pi4j {
    digitalInput(PIN_BUTTON) {
        id("button")
        name("Press button")
        pull(PullResistance.PULL_DOWN)
        debounce(3000L)
        piGpioProvider()
      }.onLow {
        pressCount++
        +"Button was pressed for the ${pressCount}th time"
      }
    }
    
    digitalOutput(PIN_LED) {
        id("led")
        name("LED Flasher")
        shutdown(DigitalState.LOW)
        initial(DigitalState.LOW)
        piGpioProvider()
      }.run {
        while (pressCount < 5) {
          if (isHigh) {
            +"LED low"
            low()
          } else {
            +"LED high"
            high()
          }
          Thread.sleep((500L / (pressCount + 1)))
        }
      }
    }
  }
}
```


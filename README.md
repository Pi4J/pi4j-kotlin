# Pi4J - Kotlin

Kotlin Interface & DSL for [Pi4J](https://github.com/Pi4J/pi4j).

For Pi4J V1 Kotlin Bindings, check [Pi4K](https://github.com/mhashim6/Pi4K) (no longer supported).

[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/com.pi4j/pi4j-ktx.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.pi4j%22%20AND%20a:%22pi4j-ktx%22)
[![License](https://img.shields.io/github/license/pi4j/pi4j-v2)](http://www.apache.org/licenses/LICENSE-2.0)
![GitHub Actions build state](https://github.com/pi4j/pi4j-kotlin/workflows/Build%20pi4j-kotlin/badge.svg)
[![libs.tech recommends](https://libs.tech/project/535721641/badge.svg)](https://libs.tech/project/535721641/pi4j-kotlin)

## Documentation

Full documentation can be found on the [website](https://www.pi4j.com/kotlin/)

## Modules

| Module | Artifact | Description |
|--------|----------|-------------|
| `lib` | `pi4j-ktx` | Core Kotlin DSL for Pi4J (digital/analog GPIO, PWM, I2C) |
| `lib-serial` | `pi4j-ktx-serial` | Serial communication DSL wrapping [jSerialComm](https://fazecast.github.io/jSerialComm/) |

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
    }.onLow {
      pressCount++
      +"Button was pressed for the ${pressCount}th time"
    }

    digitalOutput(PIN_LED) {
      id("led")
      name("LED Flasher")
      shutdown(DigitalState.LOW)
      initial(DigitalState.LOW)
    }.run {
      while (pressCount < 5) {
        +"LED ${state()}"
        toggle()
        sleep(500L / (pressCount + 1))
      }
    }
  }
}
```

### Serial

Serial communication is provided by the `pi4j-ktx-serial` module, which wraps [jSerialComm](https://fazecast.github.io/jSerialComm/) (independent of Pi4J core since serial was removed in Pi4J 4.0.0).

**Gradle setup:**

``` kotlin
dependencies {
  implementation("com.pi4j:pi4j-ktx-serial:4.0.0")
  implementation("com.fazecast:jSerialComm:2.11.0")
}
```

**Usage:**

``` kotlin
import com.pi4j.ktx.io.serial.serial
import com.pi4j.ktx.io.serial.open

serial("/dev/ttyS0") {
  baudRate = 115200
  dataBits = 8
  // stopBits, parity, flowControl also available
}.open {
  // `this` is a jSerialComm SerialPort
  // port is automatically closed when the block exits
  outputStream.write("Hello\n".toByteArray())
  val response = inputStream.bufferedReader().readLine()
}
```

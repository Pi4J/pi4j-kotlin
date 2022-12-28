import com.pi4j.io.gpio.digital.DigitalState
import com.pi4j.io.gpio.digital.PullResistance
import com.pi4j.ktx.console
import com.pi4j.ktx.io.digital.digitalInput
import com.pi4j.ktx.io.digital.digitalOutput
import com.pi4j.ktx.io.digital.onLow
import com.pi4j.ktx.io.digital.piGpioProvider
import com.pi4j.ktx.pi4jAsync
import kotlinx.coroutines.delay

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private const val PIN_BUTTON = 24 // PIN 18 = BCM 24
private const val PIN_LED = 22 // PIN 15 = BCM 22
private var pressCount = 0

/**
 * This application blinks a led and counts the number the button is pressed. The blink speed increases with each
 * button press, and after 5 presses the application finishes.
 *
 * This example fully describes the basic usage of Pi4J-Kotlin + Coroutines
 *
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
fun main() {
    pi4jAsync {
        console {
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

            digitalOutput(PIN_LED) {
                id("led")
                name("LED Flasher")
                shutdown(DigitalState.LOW)
                initial(DigitalState.LOW)
                piGpioProvider()
            }.run {
                while (pressCount < 5) {
                    +"LED ${state()}"
                    toggle()
                    delay(500L / (pressCount + 1))
                }
            }
        }
    }
}
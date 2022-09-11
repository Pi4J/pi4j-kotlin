import com.pi4j.io.gpio.digital.DigitalState
import com.pi4j.io.gpio.digital.PullResistance
import com.pi4j.pi4k.*
import com.pi4j.pi4k.io.digital.*


/*-
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4K :: EXAMPLE  :: Kotlin Sample Code
 * FILENAME      :  MinimalExample.kt
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  https://pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2021 Pi4J
 * %%
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
 * #L%
 */

private const val PIN_BUTTON = 24 // PIN 18 = BCM 24
private const val PIN_LED = 22 // PIN 15 = BCM 22
private var pressCount = 0

/**
 * This application blinks a led and counts the number the button is pressed. The blink speed increases with each
 * button press, and after 5 presses the application finishes.
 *
 * @throws java.lang.Exception if any.
 *
 * This example fully describes the base usage of Pi4K by providing extensive comments in each step.
 *
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 */

fun main() {
    // Use Pi4K com.pi4j.pi4k.console wrapper/helper
    console {
        // Print program title/header
        title("<-- The Pi4J Project -->", "Minimal Example project")

        // ************************************************************
        //
        // WELCOME TO Pi4J:
        //
        // Here we will use this getting started example to
        // demonstrate the basic fundamentals of the Pi4J library with the Pi4K Kotlin DSL.
        //
        // This example is to introduce you to the boilerplate
        // logic and concepts required for all applications using
        // the Pi4J library.  This example will do use some basic I/O.
        // Check the pi4j-examples project to learn about all the I/O
        // functions of Pi4J.
        //
        // ************************************************************

        // ------------------------------------------------------------
        // Initialize the Pi4J Runtime Context
        // ------------------------------------------------------------
        // Before you can use Pi4J you must initialize a new runtime
        // context.
        //
        // The 'com.pi4j.pi4k.withinAutoContext'
        // method will automatically load all available Pi4J
        // extensions found in the application's classpath which
        // may include 'Platforms' and 'I/O Providers'
        pi4j {
            // ------------------------------------------------------------
            // Output Pi4J Context information
            // ------------------------------------------------------------
            // The created Pi4J Context initializes platforms, providers
            // and the I/O registry. To help you to better understand this
            // approach, we print out the info of these. This can be removed
            // from your own application.
            // OPTIONAL
            printLoadedPlatforms(this)
            printDefaultPlatform(this)
            printProviders(this)

            // Here we will create I/O interfaces for a (GPIO) digital output
            // and input pin. We define the 'provider' to use PiGpio to control
            // the GPIO.
            digitalInput(PIN_BUTTON) {
                id("button")
                name("Press button")
                pull(PullResistance.PULL_DOWN)
                debounce(3000L)
                mockProvider()
            }.onLow {
                pressCount++
                +"Button was pressed for the ${pressCount}th time"
            }

            digitalOutput(PIN_LED) {
                id("led")
                name("LED Flasher")
                shutdown(DigitalState.LOW)
                initial(DigitalState.LOW)
                mockProvider()
            }.run {
                // OPTIONAL: print the registry
                printRegistry(this@pi4j)
                while (pressCount < 5) {
                    if (equals(DigitalState.HIGH)) {
                        +"LED low"
                        low()
                    } else {
                        +"LED high"
                        high()
                    }
                    Thread.sleep((500L / (pressCount + 1)))
                }
            }
            // ------------------------------------------------------------
            // Terminate the Pi4J library
            // ------------------------------------------------------------
            // We we are all done and want to exit our application, we must
            // call the 'shutdown()' function on the Pi4J static helper class.
            // This will ensure that all I/O instances are properly shutdown,
            // released by the the system and shutdown in the appropriate
            // manner. Terminate will also ensure that any background
            // threads/processes are cleanly shutdown and any used memory
            // is returned to the system.
            // shutdown() is called automatically after the block execution
        }
    }
}

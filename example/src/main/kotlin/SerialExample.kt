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

import com.pi4j.io.serial.FlowControl
import com.pi4j.io.serial.Parity
import com.pi4j.io.serial.StopBits
import com.pi4j.ktx.console
import com.pi4j.ktx.io.open
import com.pi4j.ktx.io.piGpioSerialProvider
import com.pi4j.ktx.io.serial
import com.pi4j.ktx.pi4j
import java.lang.Thread.sleep

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 26/02/2023
 */

fun main() {
    pi4j {
        serial("/dev/ttyS0") {
            use_9600_N81()
            dataBits_8()
            parity(Parity.NONE)
            stopBits(StopBits._1)
            flowControl(FlowControl.NONE)
            piGpioSerialProvider()
        }.open {
            console {
                +"Waiting till serial port is open"
                while (!isOpen) {
                    print(".")
                    sleep(250)
                }
                println()

                +"Serial port is open"
                startDaemon {
                    inputStream.bufferedReader().use {
                        while (true) {
                            if (available() != 0) sleep(10)
                            else buildString {
                                (0 until available()).forEach { _ ->
                                    readByte().let { b ->
                                        // All non-string bytes are handled as line breaks
                                        if (b < 32) return@forEach
                                        else append(b.toInt().toChar())
                                    }
                                }
                            }.also { +"Data: '$it'" }
                        }
                    }
                }
                while (isOpen) sleep(500)
            }
        }
    }
}

fun startDaemon(runnable: Runnable) = Thread(runnable).apply {
    isDaemon = true
    start()
}
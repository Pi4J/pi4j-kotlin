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

import com.pi4j.ktx.console
import com.pi4j.ktx.io.serial.open
import com.pi4j.ktx.io.serial.serial
import java.lang.Thread.sleep

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */

fun main() {
    serial("/dev/ttyS0") {
        baudRate = 9600
        dataBits = 8
    }.open {
        console {
            +"Serial port is open"
            startDaemon {
                inputStream.bufferedReader().use { reader ->
                    while (true) {
                        val line = reader.readLine() ?: break
                        +"Data: '$line'"
                    }
                }
            }
            while (isOpen) sleep(500)
        }
    }
}

fun startDaemon(runnable: Runnable) = Thread(runnable).apply {
    isDaemon = true
    start()
}

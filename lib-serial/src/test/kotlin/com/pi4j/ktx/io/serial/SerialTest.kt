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
package com.pi4j.ktx.io.serial

import com.fazecast.jSerialComm.SerialPort
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class SerialTest {

    @Test
    fun `test serial config defaults`() {
        val config = SerialConfig()
        assertEquals(9600, config.baudRate)
        assertEquals(8, config.dataBits)
        assertEquals(SerialPort.ONE_STOP_BIT, config.stopBits)
        assertEquals(SerialPort.NO_PARITY, config.parity)
        assertEquals(SerialPort.FLOW_CONTROL_DISABLED, config.flowControl)
    }

    @Test
    fun `test serial config custom values`() {
        val config = SerialConfig().apply {
            baudRate = 115200
            dataBits = 7
            stopBits = SerialPort.TWO_STOP_BITS
            parity = SerialPort.EVEN_PARITY
            flowControl = SerialPort.FLOW_CONTROL_RTS_ENABLED
        }
        assertEquals(115200, config.baudRate)
        assertEquals(7, config.dataBits)
        assertEquals(SerialPort.TWO_STOP_BITS, config.stopBits)
        assertEquals(SerialPort.EVEN_PARITY, config.parity)
        assertEquals(SerialPort.FLOW_CONTROL_RTS_ENABLED, config.flowControl)
    }
}

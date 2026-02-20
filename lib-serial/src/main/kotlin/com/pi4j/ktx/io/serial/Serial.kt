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

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */

@DslMarker
annotation class SerialConfigMarker

@SerialConfigMarker
class SerialConfig {
    var baudRate: Int = 9600
    var dataBits: Int = 8
    var stopBits: Int = SerialPort.ONE_STOP_BIT
    var parity: Int = SerialPort.NO_PARITY
    var flowControl: Int = SerialPort.FLOW_CONTROL_DISABLED
}

inline fun serial(device: String, block: SerialConfig.() -> Unit): SerialPort {
    val config = SerialConfig().apply(block)
    return SerialPort.getCommPort(device).apply {
        setBaudRate(config.baudRate)
        setNumDataBits(config.dataBits)
        setNumStopBits(config.stopBits)
        setParity(config.parity)
        setFlowControl(config.flowControl)
    }
}

fun serial(device: String): SerialPort = serial(device) {}

inline fun SerialPort.open(action: SerialPort.() -> Unit) = apply {
    openPort()
    try {
        action()
    } finally {
        closePort()
    }
}

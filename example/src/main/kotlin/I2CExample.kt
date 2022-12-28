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
import com.pi4j.ktx.io.i2c
import com.pi4j.ktx.io.linuxFsI2CProvider
import com.pi4j.ktx.io.setPin
import com.pi4j.ktx.pi4j
import com.pi4j.ktx.utils.binStr
import java.lang.Thread.sleep

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


private const val TCA9534_REG_ADDR_OUT_PORT: Int = 0x01
private const val TCA9534_REG_ADDR_CFG: Int = 0x03

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 28/12/2022
 */
fun main() {
    pi4j {
        i2c(1, 0x3f) {
            id("TCA9534")
            linuxFsI2CProvider()
        }.use { tca9534Dev ->
            val config = tca9534Dev.readRegister(TCA9534_REG_ADDR_CFG)
            check(config >= 0) {
                "Failed to read configuration from address 0x${"%02x".format(TCA9534_REG_ADDR_CFG)}"
            }

            var currentState = tca9534Dev.readRegister(TCA9534_REG_ADDR_OUT_PORT)
            if (config != 0x00) {
                println(
                    "TCA9534 is not configured as OUTPUT, setting register 0x${"%02x".format(TCA9534_REG_ADDR_CFG)} to 0x00"
                )
                currentState = 0x00
                tca9534Dev.writeRegister(TCA9534_REG_ADDR_OUT_PORT, currentState)
                tca9534Dev.writeRegister(TCA9534_REG_ADDR_CFG, 0x00)
            }

            tca9534Dev.run {
                // bit 8, is pin 1 on the board itself, so set pins in reverse:
                console {
                    currentState = setPin(currentState, 8, TCA9534_REG_ADDR_OUT_PORT)
                    +"Setting TCA9534 to new state  ${currentState.binStr()}"
                    sleep(500L)
                    currentState = setPin(currentState, 8, TCA9534_REG_ADDR_OUT_PORT, false)
                    +"Setting TCA9534 to new state  ${currentState.binStr()}"
                    sleep(500L)
                    currentState = setPin(currentState, 7, TCA9534_REG_ADDR_OUT_PORT)
                    +"Setting TCA9534 to new state  ${currentState.binStr()}"
                    sleep(500L)
                    currentState = setPin(currentState, 7, TCA9534_REG_ADDR_OUT_PORT, false)
                    +"Setting TCA9534 to new state  ${currentState.binStr()}"
                    sleep(500L)
                }
            }
        }
    }

}

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

package com.pi4j.ktx.io

import com.pi4j.context.Context
import com.pi4j.io.i2c.I2C
import com.pi4j.io.i2c.I2CConfigBuilder
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 28/12/2022
 */

inline fun Context.i2c(bus: Int, device: Int, block: I2CConfigBuilder.() -> Unit): I2C =
    create(I2C.newConfigBuilder(this).run {
        bus(bus)
        device(device)
        block()
        build()
    })

fun I2C.setPin(currentState: Int, pin: Int, regOutPort: Int, high: Boolean = true): Int {
    val newState: Int =
        if (high) {
            currentState or (1 shl pin)
        } else {
            currentState and (1 shl pin).inv()
        }

    writeRegister(regOutPort, newState)
    return newState
}

fun I2CConfigBuilder.mockI2cProvider() {
    provider(Provider.MOCK_I2C.id)
}

fun I2CConfigBuilder.linuxFsI2CProvider() {
    provider(Provider.LINUX_FS_I2C.id)
}
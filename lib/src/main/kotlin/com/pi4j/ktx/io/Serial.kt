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
import com.pi4j.io.serial.Serial
import com.pi4j.io.serial.SerialConfigBuilder
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 26/02/2023
 */

inline fun Context.serial(device: String, block: SerialConfigBuilder.() -> Unit): Serial =
    create(Serial.newConfigBuilder(this).run {
        device(device)
        block()
        build()
    })

inline fun Serial.open(action: Serial.() -> Unit) = apply {
    open()
    action()
}

fun SerialConfigBuilder.mockSerialProvider() {
    provider(Provider.MOCK_SERIAL.id)
}

fun SerialConfigBuilder.piGpioSerialProvider() {
    provider(Provider.PI_GPIO_SERIAL.id)
}
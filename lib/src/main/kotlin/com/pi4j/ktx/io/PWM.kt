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
import com.pi4j.io.pwm.Pwm
import com.pi4j.io.pwm.PwmConfigBuilder
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */

inline fun Context.pwm(address: Int, block: PwmConfigBuilder.() -> Unit): Pwm =
    create(PwmConfigBuilder.newInstance(this).run {
        address(address)
        block()
        build()
    })

fun Context.pwm(address: Int): Pwm = pwm(address) {}


fun PwmConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_PWM.id)
}

fun PwmConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_PWM.id)
}

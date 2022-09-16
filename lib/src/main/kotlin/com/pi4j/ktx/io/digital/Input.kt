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
package com.pi4j.ktx.io.digital

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.*
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

inline fun Context.digitalInput(address: Int, block: DigitalInputConfigBuilder.() -> Unit): DigitalInput =
    create(DigitalInput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

fun Context.digitalInput(address: Int): DigitalInput = digitalInput(address) {}

inline fun DigitalInput.listen(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            block(e)
        })
    }

inline fun DigitalInput.onHigh(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            if (e.state() == DigitalState.HIGH) block(e)
        })
    }

inline fun DigitalInput.onLow(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            if (e.state() == DigitalState.LOW) block(e)
        })
    }

fun DigitalInputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_DIGITAL_INPUT.id)
}

fun DigitalInputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_DIGITAL_INPUT.id)
}

val DigitalInput.isLow
    get() = state() == DigitalState.LOW
val DigitalInput.isHigh
    get() = state() == DigitalState.HIGH
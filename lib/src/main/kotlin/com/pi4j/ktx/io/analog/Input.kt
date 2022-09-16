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
package com.pi4j.ktx.io.analog

import com.pi4j.context.Context
import com.pi4j.io.gpio.analog.AnalogInput
import com.pi4j.io.gpio.analog.AnalogInputConfigBuilder
import com.pi4j.io.gpio.analog.AnalogValueChangeEvent
import com.pi4j.io.gpio.analog.AnalogValueChangeListener
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 12/9/22
 */

inline fun Context.analogInput(address: Int, block: AnalogInputConfigBuilder.() -> Unit): AnalogInput =
    create(AnalogInput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

fun Context.analogInput(address: Int): AnalogInput = analogInput(address) {}

inline fun AnalogInput.listen(crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            block(e)
        })
    }

inline fun AnalogInput.whenInRange(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() in range) block(e)
        })
    }

inline fun AnalogInput.whenOutOfRange(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() !in range) block(e)
        })
    }

inline fun AnalogInput.onMax(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() == range.last) block(e)
        })
    }

inline fun AnalogInput.onMin(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() == range.first) block(e)
        })
    }

fun AnalogInputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_ANALOG_INPUT.id)
}

fun AnalogInputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_ANALOG_INPUT.id)
}

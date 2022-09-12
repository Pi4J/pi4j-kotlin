package com.pi4j.ktx.io.analog

import com.pi4j.context.Context
import com.pi4j.io.gpio.analog.AnalogOutput
import com.pi4j.io.gpio.analog.AnalogOutputConfigBuilder
import com.pi4j.io.gpio.analog.AnalogValueChangeEvent
import com.pi4j.io.gpio.analog.AnalogValueChangeListener
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 12/9/22
 */

inline fun Context.analogOutput(address: Int, block: AnalogOutputConfigBuilder.() -> Unit): AnalogOutput =
    create(AnalogOutput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

inline fun AnalogOutput.listen(crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            block(e)
        })
    }

inline fun AnalogOutput.whenInRange(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() in range) block(e)
        })
    }

inline fun AnalogOutput.whenOutOfRange(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() !in range) block(e)
        })
    }

inline fun AnalogOutput.onMax(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() == range.last) block(e)
        })
    }

inline fun AnalogOutput.onMin(range: IntRange, crossinline block: (AnalogValueChangeEvent<*>) -> Unit) =
    run {
        addListener(AnalogValueChangeListener { e: AnalogValueChangeEvent<*> ->
            if (e.value() == range.first) block(e)
        })
    }

fun AnalogOutputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_ANALOG_OUTPUT.id)
}

fun AnalogOutputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_ANALOG_OUTPUT.id)
}
package com.pi4j.ktx.io.digital

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.*
import com.pi4j.ktx.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

inline fun Context.digitalOutput(address: Int, block: DigitalOutputConfigBuilder.() -> Unit): DigitalOutput =
    create(DigitalOutput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

fun Context.digitalOutput(address: Int): DigitalOutput = digitalOutput(address) {}

inline fun DigitalOutput.listen(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            block(e)
        })
    }

inline fun DigitalOutput.onHigh(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            if (e.state() == DigitalState.HIGH) block(e)
        })
    }

inline fun DigitalOutput.onLow(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            if (e.state() == DigitalState.LOW) block(e)
        })
    }


fun DigitalOutputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_DIGITAL_OUTPUT.id)
}

fun DigitalOutputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_DIGITAL_OUTPUT.id)
}


val DigitalOutput.isLow
    get() = state() == DigitalState.LOW
val DigitalOutput.isHigh
    get() = state() == DigitalState.HIGH
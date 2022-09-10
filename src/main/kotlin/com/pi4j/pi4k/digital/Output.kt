package com.pi4j.pi4k.digital

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.*
import com.pi4j.pi4k.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

inline fun Context.digitalOutput(address: Int, block: DigitalOutputConfigBuilder.() -> Unit) =
    create(DigitalOutput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

inline fun DigitalOutput.listen(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            block(e)
        })
    }

// TODO: listenOnState

fun DigitalOutputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_DIGITAL_OUTPUT.id)
}

fun DigitalOutputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_DIGITAL_OUTPUT.id)
}
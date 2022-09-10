package com.pi4j.pi4k.digital

import com.pi4j.context.Context
import com.pi4j.io.gpio.digital.DigitalInput
import com.pi4j.io.gpio.digital.DigitalInputConfigBuilder
import com.pi4j.io.gpio.digital.DigitalStateChangeEvent
import com.pi4j.io.gpio.digital.DigitalStateChangeListener
import com.pi4j.pi4k.utils.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

inline fun Context.digitalInput(address: Int, block: DigitalInputConfigBuilder.() -> Unit) =
    create(DigitalInput.newConfigBuilder(this).run {
        address(address)
        block()
        build()
    })

inline fun DigitalInput.listen(crossinline block: (DigitalStateChangeEvent<*>) -> Unit) =
    run {
        addListener(DigitalStateChangeListener { e: DigitalStateChangeEvent<*> ->
            block(e)
        })
    }

// TODO: listenOnState

fun DigitalInputConfigBuilder.mockProvider() = apply {
    provider(Provider.MOCK_DIGITAL_INPUT.id)
}

fun DigitalInputConfigBuilder.piGpioProvider() = apply {
    provider(Provider.PI_GPIO_DIGITAL_INPUT.id)
}
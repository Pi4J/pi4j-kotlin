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

package com.pi4j.ktx.utils

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

enum class Provider(val id: String) {
    MOCK_DIGITAL_INPUT("mock-digital-input"),
    MOCK_DIGITAL_OUTPUT("mock-digital-output"),
    PI_GPIO_DIGITAL_INPUT("pigpio-digital-input"),
    PI_GPIO_DIGITAL_OUTPUT("pigpio-digital-output"),
    MOCK_ANALOG_INPUT("mock-analog-input"),
    MOCK_ANALOG_OUTPUT("mock-analog-output"),
    PI_GPIO_ANALOG_INPUT("pigpio-analog-input"),
    PI_GPIO_ANALOG_OUTPUT("pigpio-analog-output"),
    MOCK_PWM("mock-pwm"),
    PI_GPIO_PWM("pigpio-pwm"),
}
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
    MOCK_I2C("mock-i2c"),
    LINUX_FS_I2C("linuxfs-i2c"),
    MOCK_SERIAL("mock-serial"),
    PI_GPIO_SERIAL("pigpio-serial")
}
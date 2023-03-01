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

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.i2c.I2C
import com.pi4j.io.i2c.I2CProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 28/12/2022
 */
internal class I2CTest {
    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test i2c creation`() {
        context.run {
            val i2CProvider: I2CProvider = context.provider<I2CProvider>("mock-i2c")
            val javaI2C =
                i2CProvider.create(I2C.newConfigBuilder(context).id("TCA9534").bus(1).device(0x3f).build())

            val kotlinI2C = i2c(2, 0x3f) {
                mockI2CProvider()
            }

            assertEquals(javaI2C::class.java, kotlinI2C::class.java)
            assertEquals(2, kotlinI2C.bus)

            assertThrows<IOAlreadyExistsException> {
                i2CProvider.create(I2C.newConfigBuilder(context).id("TCA9534").bus(1).device(0x4f).build())
                i2c(1, 0x4f) {
                    id("TCA9534")
                    mockI2CProvider()
                }
            }
        }
    }


    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
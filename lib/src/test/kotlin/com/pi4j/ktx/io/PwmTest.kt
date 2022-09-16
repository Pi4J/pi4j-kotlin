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
import com.pi4j.io.pwm.PwmConfigBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.*


/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class PwmTest {

    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test pwm creation`() {
        context.run {
            val javaPin = create(PwmConfigBuilder.newInstance(this).address(24).id("test-pin").build())
            val kotlinPin = pwm(22)

            assertEquals(javaPin::class.java, kotlinPin::class.java)
            assertEquals(22, kotlinPin.address)

            assertThrows<IOAlreadyExistsException> {
                create(PwmConfigBuilder.newInstance(this).address(26).id("test-pin").build())
                pwm(23) {
                    id("test-pin")
                }
            }
        }
    }

    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
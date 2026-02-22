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

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.gpio.analog.AnalogOutput
import com.pi4j.plugin.mock.provider.gpio.analog.MockAnalogOutputProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class AnalogOutputTest {
    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newContextBuilder()
            .add(MockAnalogOutputProvider.newInstance())
            .build();
    }

    @Test
    fun `test analog output creation`() {
        context.run {
            val kotlinPin = analogOutput(22)
            assertEquals(22, kotlinPin.config().bcm())

            assertThrows<IOAlreadyExistsException> {
                analogOutput(22) {
                    id("conflicting-pin")
                }
            }
        }
    }

    @Test
    fun `test analog output listeners`() {
        context.run {
            val latch = CountDownLatch(4)
            val kotlinPin = analogOutput(22).run {

                onMax(0..5) {
                    assertEquals(5, it.value())
                    latch.countDown()
                }
                onMin(0..5) {
                    assertEquals(0, it.value())
                    latch.countDown()
                }
                whenInRange(0..5) {
                    assertTrue { it.value() in 0..5 }
                    latch.countDown()
                }

                whenOutOfRange(0..5) {
                    assertTrue { it.value() !in 0..5 }
                    latch.countDown()
                }
            }
            kotlinPin.value(5)
            kotlinPin.value(0)
            kotlinPin.value(3)
            kotlinPin.value(30)
            assertTrue(latch.await(2, TimeUnit.SECONDS), "Listeners should have fired")
        }
    }

    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}

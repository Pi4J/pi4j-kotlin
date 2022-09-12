package com.pi4j.ktx.io.analog

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.gpio.analog.AnalogOutput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class AnalogOutputTest {
    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test analog output creation`() {
        context.run {
            val javaPin = create(AnalogOutput.newConfigBuilder(this).address(24).id("test-pin").build())
            val kotlinPin = analogOutput(22) { }

            assertEquals(javaPin::class.java, kotlinPin::class.java)
            assertEquals(22, kotlinPin.address)

            assertThrows<IOAlreadyExistsException> {
                create(AnalogOutput.newConfigBuilder(this).address(26).id("test-pin").build())
                analogOutput(23) {
                    id("test-pin")
                }
            }
        }
    }

    @Test
    fun `test analog output listeners`() {
        context.run {
            val kotlinPin = analogOutput(22) {}.run {

                onMax(0..5) {
                    assertEquals(5, it.value())
                }
                onMin(0..5) {
                    assertEquals(0, it.value())
                }
                whenInRange(0..5) {
                    assertTrue { it.value() in 0..5 }
                }

                whenOutOfRange(0..5) {
                    assertTrue { it.value() !in 0..5 }
                }
            }
            kotlinPin.value(5)
            kotlinPin.value(0)
            kotlinPin.value(3)
            kotlinPin.value(30)
            Thread.sleep(1500L)
        }
    }

    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
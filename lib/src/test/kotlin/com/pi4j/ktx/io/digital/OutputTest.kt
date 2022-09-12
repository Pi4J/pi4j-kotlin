package com.pi4j.ktx.io.digital

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.gpio.digital.DigitalOutput
import com.pi4j.io.gpio.digital.DigitalState
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class DigitalOutputTest {
    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test digital output creation`() {
        context.run {
            val javaPin = create(DigitalOutput.newConfigBuilder(this).address(24).id("test-pin").build())
            val kotlinPin = digitalOutput(22)

            assertEquals(javaPin::class.java, kotlinPin::class.java)
            assertEquals(22, kotlinPin.address)

            assertThrows<IOAlreadyExistsException> {
                create(DigitalOutput.newConfigBuilder(this).address(26).id("test-pin").build())
                digitalOutput(23) {
                    id("test-pin")
                }
            }
        }
    }

    @Test
    fun `test digital output listeners`() {
        context.run {
            val kotlinPin = digitalOutput(22).run {
                onLow {
                    assertEquals(DigitalState.LOW, it.state())
                }
                onHigh {
                    assertEquals(DigitalState.HIGH, it.state())
                }
            }
            kotlinPin.low()
            kotlinPin.high()
            Thread.sleep(1000L)
        }
    }


    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
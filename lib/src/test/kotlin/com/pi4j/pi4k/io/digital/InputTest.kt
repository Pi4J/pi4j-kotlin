package com.pi4j.pi4k.io.digital

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.gpio.digital.DigitalInput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class InputTest {

    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test digital input creation`() {
        context.run {
            val javaPin = create(DigitalInput.newConfigBuilder(this).address(24).id("test-pin").build())
            val kotlinPin = digitalInput(22) { }

            assertEquals(javaPin::class.java, kotlinPin::class.java)
            assertEquals(22, kotlinPin.address)

            assertThrows<IOAlreadyExistsException> {
                create(DigitalInput.newConfigBuilder(this).address(26).id("test-pin").build())
                digitalInput(23) {
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
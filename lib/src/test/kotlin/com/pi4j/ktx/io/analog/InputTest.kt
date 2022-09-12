package com.pi4j.ktx.io.analog

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.io.exception.IOAlreadyExistsException
import com.pi4j.io.gpio.analog.AnalogInput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */
internal class AnalogInputTest {

    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test analog input creation`() {
        context.run {
            val javaPin = create(AnalogInput.newConfigBuilder(this).address(24).id("test-pin").build())
            val kotlinPin = analogInput(22)

            assertEquals(javaPin::class.java, kotlinPin::class.java)
            assertEquals(22, kotlinPin.address)

            assertThrows<IOAlreadyExistsException> {
                create(AnalogInput.newConfigBuilder(this).address(26).id("test-pin").build())
                analogInput(23) {
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
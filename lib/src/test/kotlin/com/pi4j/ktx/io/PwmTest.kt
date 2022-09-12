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
package com.pi4j.ktx

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.plugin.mock.platform.MockPlatform
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider
import org.junit.jupiter.api.Test
import kotlin.test.*

/**
 * @author Muhammad Hashim (mhashim6) ([https://mhashim6.me](https://mhashim6.me))
 */
internal class ContextTest {

    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test auto-shutdown`() {
        pi4j {
            println("I'm in pi4j auto context")
        }.also {
            assertTrue { it.isShutdown }
        }
    }

    @Test
    fun `test generics`() {
        context.run {
            assertEquals(hasPlatform(MockPlatform::class.java), hasPlatform<MockPlatform>())
            assertEquals(hasProvider(MockPwmProvider::class.java), hasProvider<MockPwmProvider>())
            assertSame(provider(MockPwmProvider::class.java), provider<MockPwmProvider>())
        }
    }

    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
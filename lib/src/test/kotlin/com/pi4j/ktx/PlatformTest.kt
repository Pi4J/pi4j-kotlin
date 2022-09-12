package com.pi4j.ktx

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.plugin.mock.platform.MockPlatform
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider
import org.junit.jupiter.api.Test
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertSame

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */

internal class PlatformTest {

    private lateinit var context: Context

    @BeforeTest
    fun setup(){
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test generics`() {
        context.platform<MockPlatform>().run {
            assertEquals(hasProvider(MockPwmProvider::class.java), hasProvider<MockPwmProvider>())
            assertSame(provider(MockPwmProvider::class.java), provider<MockPwmProvider>())
        }
    }

    @AfterTest
    fun tearDown(){
        context.shutdown()
    }
}
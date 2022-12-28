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
package com.pi4j.ktx

import com.pi4j.Pi4J
import com.pi4j.context.Context
import com.pi4j.plugin.mock.platform.MockPlatform
import com.pi4j.plugin.mock.provider.pwm.MockPwmProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import org.junit.jupiter.api.Test
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.util.concurrent.Executors
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
    fun `test pi4jAsync custom coroutine scope`() {
        val executor = Executors.newSingleThreadExecutor()
        var thread = currentThread().id
        println(thread)
        executor.execute {
            thread = currentThread().id
        }

        pi4jAsync(CoroutineScope(executor.asCoroutineDispatcher())) {
            println("I'm in pi4j auto context")

            assertEquals(thread, currentThread().id)

            val now = currentTimeMillis()
            delay(1000L)
            val delay = currentTimeMillis() - now
            println("$delay")
            assertTrue { delay in 1000L..1100L }
        }.also {
            assertTrue { it.isShutdown }
        }
    }

    @Test
    fun `test pi4jAsync coroutine scope`() {
        val thread = currentThread().id
        println(thread)

        pi4jAsync {
            println("I'm in pi4j auto context")
            delay(1000L)
            assertNotEquals(thread, currentThread().id)
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
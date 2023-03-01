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
import com.pi4j.plugin.mock.provider.serial.MockSerial

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 26/02/2023
 */
internal class SerialTest {
    private lateinit var context: Context

    @BeforeTest
    fun setup() {
        context = Pi4J.newAutoContext()
    }

    @Test
    fun `test serial creation`() {
        context.run {
            val kotlinSerial = serial("/dev/ttyS0") {
                mockSerialProvider()
            }

            assertEquals(MockSerial::class, kotlinSerial::class)

            assertThrows<IOAlreadyExistsException> {
                serial("/dev/ttyS0") {
                    id("conflictingSerial")
                    mockSerialProvider()
                }
            }
        }
    }


    @AfterTest
    fun tearDown() {
        context.shutdown()
    }
}
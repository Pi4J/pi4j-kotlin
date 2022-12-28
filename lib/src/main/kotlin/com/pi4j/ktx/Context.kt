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
import com.pi4j.config.Config
import com.pi4j.context.Context
import com.pi4j.context.ContextBuilder
import com.pi4j.io.IO
import com.pi4j.platform.Platform
import com.pi4j.provider.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.InputStream
import java.io.Reader
import java.util.*

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */

@DslMarker
annotation class ContextBuilderMarker

@ContextBuilderMarker
class KontextBuilder : ContextBuilder by ContextBuilder.newInstance() {
    operator fun Platform.unaryPlus() {
        add(this)
    }

    operator fun Provider<out Provider<*, *, *>, out IO<*, *, *>, out Config<*>>?.unaryPlus() {
        add(this)
    }

    operator fun Properties.unaryPlus() {
        add(this)
    }

    operator fun Pair<String, String>.unaryPlus() {
        addProperty(this.first, this.second)
    }

    operator fun File.unaryPlus() {
        addProperties(this)
    }

    operator fun Reader.unaryPlus() {
        addProperties(this)
    }

    operator fun InputStream.unaryPlus() {
        addProperties(this)
    }
}

/**
 * Creates a new [Context] using [Pi4J.newAutoContext] and uses it in execution.
 * Automatically calls [Context.shutdown] after [block] execution
 * @param block runs on a [Context] Receiver
 */
inline fun pi4j(block: Context.() -> Unit): Context {
    val context = Pi4J.newAutoContext()
    context.run(block)
    context.shutdown()
    return context
}

/**
 * Coroutine variant of [pi4j]
 *
 * Creates a new [Context] using [Pi4J.newAutoContext] and uses it in execution.
 * Automatically calls [Context.shutdown] after [block] execution
 * @param scope within which the coroutines will run
 * @param block runs on a [Context] Receiver
 */
fun pi4jAsync(scope: CoroutineScope = CoroutineScope(Dispatchers.IO), block: suspend Context.() -> Unit): Context {
    return runBlocking {
        pi4j {
            scope.async {
                block()
            }.await()
        }
    }
}

inline fun buildContext(builder: KontextBuilder.() -> Unit): Context {
    return KontextBuilder().run {
        builder.invoke(this)
        build()
    }
}

inline fun <reified T : Platform> Context.hasPlatform() = hasPlatform(T::class.java)
inline fun <reified T : Provider<*, *, *>> Context.hasProvider() = hasProvider(T::class.java)
inline fun <reified T : Provider<*, *, *>> Context.provider(): T = provider(T::class.java)


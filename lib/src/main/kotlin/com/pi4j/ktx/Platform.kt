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

import com.pi4j.io.IO
import com.pi4j.io.IOConfig
import com.pi4j.platform.Platform
import com.pi4j.provider.Provider

/**
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>)
 */

inline fun <reified T : Provider<*, *, *>> Platform.hasProvider() = hasProvider(T::class.java)
inline fun <reified T : Provider<*, *, *>> Platform.provider(): T = provider(T::class.java)
inline fun <reified I : IO<*, *, *>> Platform.createFrom(config: IOConfig<*>): I = create(config, I::class.java )
inline fun <reified I : IO<*, *, *>> Platform.createFrom(id: String): I = create(id, I::class.java )

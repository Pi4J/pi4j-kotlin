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

package com.pi4j.ktx

import com.pi4j.context.Context
import com.pi4j.platform.Platform
import com.pi4j.util.Console

/**
 * @author Frank Delporte (<a href="https://www.webtechie.be">https://www.webtechie.be</a>)
 * @author Muhammad Hashim (mhashim6) (<a href="https://mhashim6.me">https://mhashim6.me</a>) on 10/9/22
 */


@DslMarker
annotation class KonsoleMarker

@KonsoleMarker
class Konsole : Console() {
    operator fun String.unaryPlus() {
        println(this)
    }
}

inline fun console(builder: Konsole.() -> Unit) {
    Konsole().builder()
}


/**
 * Pi4J Platforms.
 *
 * Platforms are intended to represent the hardware platform where Pi4J is running.  In most cases this will be
 * the 'RaspberryPi' platform, but Pi4J supports and extensible set of platforms thus additional platforms such as
 * 'BananaPi', 'Odroid', etc can be added.
 *
 *
 * Platforms represent the physical layout of a system's hardware I/O
 * capabilities and what I/O providers the target platform supports.  For example, a 'RaspberryPi' platform supports
 * `Digital` inputs and outputs, PWM, I2C, SPI, and Serial but does not support a default provider for 'Analog'
 * inputs and outputs.
 *
 * Platforms also provide validation for the I/O pins and their capabilities for the target hardware.
 *
 * @param pi4j    [Context]
 */
fun Console.printLoadedPlatforms(pi4j: Context) {
    val platforms = pi4j.platforms()
    run {
        box("Pi4J PLATFORMS")
        println()
        platforms.describe().print(System.out)
        println()
    }
}

/**
 * Pi4J Platform (Default Platform)
 *
 * A single 'default' platform is auto-assigned during Pi4J initialization based on a weighting value provided
 * by each platform implementation at runtime. Additionally, you can override this behavior and assign your own
 * 'default' platform anytime after initialization.
 *
 * The default platform is a single platform instance from the managed platforms collection that will serve to
 * define the default I/O providers that Pi4J will use for each given I/O interface when creating and registering
 * I/O instances.
 *
 * @param pi4j    [Context]
 */
fun Console.printDefaultPlatform(pi4j: Context) {
    val platform = pi4j.platform<Platform>()
    run {
        box("Pi4J DEFAULT PLATFORM")
        println()
        platform.describe().print(System.out)
        println()
    }
}

/**
 * Pi4J Providers
 *
 * Providers are intended to represent I/O implementations and provide access to the I/O interfaces available on
 * the system. Providers 'provide' concrete runtime implementations of I/O interfaces such as:
 *
 *  * DigitalInput
 *  * DigitalOutput
 *  * AnalogInput
 *  * AnalogOutput
 *  * PWM
 *  * I2C
 *  * SPI
 *  * SERIAL
 *
 *
 * Each platform will have a default set of providers assigned to it to serve as the default providers that
 * will be used on a given platform's hardware I/O.  However, you are not limited to the providers that a
 * platform provides, you can instantiate I/O interfaces using any provider that has been registered on the
 * Pi4J system.  A good example of this is the 'AnalogInput' and 'AnalogOutput' I/O interfaces. The
 * 'RaspberryPi' does not inherently support analog I/O hardware, but with an attached ADC (Analog to Digital
 * Converter) or DAC (Digital to Analog converter) chip attached to a data bus (I2C/SPI) you may wish to use
 * Pi4J to read/write to these analog hardware interfaces.
 *
 * Providers allow for a completely flexible and extensible infrastructure enabling third-parties to build and
 * extend the capabilities of Pi4J by writing your/their own Provider implementation libraries.
 *
 * @param pi4j    [Context]
 */
fun Console.printProviders(pi4j: Context) {
    val providers = pi4j.providers()
    run {
        box("Pi4J PROVIDERS")
        println()
        providers.describe().print(System.out)
        println()
    }
}

/**
 * Pi4J Registry
 *
 * The registry stores the state of all the I/O managed by Pi4J.
 *
 * @param pi4j    [Context]
 */
fun Console.printRegistry(pi4j: Context) {
    val registry = pi4j.registry()
    run {
        box("Pi4J REGISTRY")
        println()
        registry.describe().print(System.out)
        println()
    }
}
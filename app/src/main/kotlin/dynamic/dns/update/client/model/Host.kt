package dynamic.dns.update.client.model

import java.io.Serializable
import java.time.Duration

abstract class Host(
    val hostname: String = "",
    val enableIPv4: Boolean = true,
    val enableIPv6: Boolean = true,
    val updateDelayTime: Duration
) : Serializable {

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine(super.toString())
        stringBuilder.appendLine("hostname = $hostname")
        stringBuilder.appendLine("enableIPv4 = $enableIPv4")
        stringBuilder.appendLine("enableIPv6 = $enableIPv6")
        return stringBuilder.toString()
    }

    fun sleep() {
        Thread.sleep(updateDelayTime.toMillis())
    }

    abstract fun performUpdate(looping: Boolean = false)

    abstract fun performCleaning()
}
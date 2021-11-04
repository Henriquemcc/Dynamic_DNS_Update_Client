package dynamic.dns.update.client.model

import java.io.Serializable
import java.time.Duration

/**
 * A dynamic dns host.
 * @param hostname Hostname of the Duck DNS subdomain host.
 * @param enableIPv4 Whether is to enable IPv4 update for this host.
 * @param enableIPv6 Whether is to enable IPv6 update for this host.
 * @param updateDelayTime Time interval between each infinite loop interaction to update IP address of the host.
 */
abstract class Host(
    val hostname: String = "",
    val enableIPv4: Boolean = true,
    val enableIPv6: Boolean = true,
    val updateDelayTime: Duration,
    val networkInterfacesName: List<String>? = null
) : Serializable {

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(super.toString())
        stringBuilder.append(", hostname = $hostname")
        stringBuilder.append(", enableIPv4 = $enableIPv4")
        stringBuilder.append(", enableIPv6 = $enableIPv6")
        stringBuilder.append(", update delay time = $updateDelayTime")
        return stringBuilder.toString()
    }

    /**
     * Sleep the current thread.
     */
    fun sleep() = Thread.sleep(updateDelayTime.toMillis())

    /**
     * Performs IP update to the current host.
     * @param looping Whether is run in infinite looping.
     */
    abstract fun performIpUpdate(looping: Boolean = false)

    /**
     * Performs IP cleaning to the current host.
     */
    abstract fun performIpCleaning()
}
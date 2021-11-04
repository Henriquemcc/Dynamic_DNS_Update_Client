package dynamic.dns.update.client.model

import dynamic.dns.update.client.exception.*
import dynamic.dns.update.client.network.internet.protocol.getUnicastIPv4Address
import dynamic.dns.update.client.network.internet.protocol.getUnicastIPv6Address
import dynamic.dns.update.client.network.internet.protocol.hostAddressFormatted
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.time.Duration
import javax.net.ssl.HttpsURLConnection

/**
 * A Duck DNS subdomain host.
 * @param token Duck DNS authentication token.
 */
class DuckDnsSubdomain(
    hostname: String = "",
    enableIPv4: Boolean = true,
    enableIPv6: Boolean = true,
    updateDelayTime: Duration,
    val token: String
) : Host(hostname, enableIPv4, enableIPv6, updateDelayTime) {

    private val subdomainName: String
        get() = hostname.lowercase().substring(0, hostname.lowercase().lastIndexOf(".duckdns.org"))

    override fun performIpUpdate(looping: Boolean) {

        do {
            if (enableIPv4)
                for (i in 0..1024)
                    try {
                        performUpdateIPv4()
                        break
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

            if (enableIPv6)
                for (i in 0..1024)
                    try {
                        performUpdateIPv6()
                        break
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

            if (looping)
                sleep()

        } while (looping)
    }

    override fun performIpCleaning() {
        val url = URL("https://www.duckdns.org/update?domains=$subdomainName&token=$token&clear=true")
        val connection = url.openConnection() as HttpsURLConnection
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val message = bufferedReader.readLine()
        if (!message.contains("OK")) {
            throw FailedToCleanAddressesException()
        }
    }

    /**
     * Performs IP update to IPv4.
     */
    private fun performUpdateIPv4() {
        val ipv4Address = getUnicastIPv4Address()?.hostAddressFormatted ?: throw IPv4NotFoundException()

        val url = URL("https://www.duckdns.org/update?domains=$subdomainName&token=$token&ip=$ipv4Address")
        val connection = url.openConnection() as HttpsURLConnection
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val message = bufferedReader.readLine()
        if (!message.contains("OK")) {
            throw FailedToUpdateIPv4AddressException()
        }
    }

    /**
     * Performs IP update to IPv6.
     */
    private fun performUpdateIPv6() {
        val ipv6Address = getUnicastIPv6Address()?.hostAddressFormatted ?: throw IPv6NotFoundException()

        val url = URL("https://www.duckdns.org/update?domains=$subdomainName&token=$token&ipv6=$ipv6Address")
        val connection = url.openConnection() as HttpsURLConnection
        val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
        val message = bufferedReader.readLine()
        if (!message.contains("OK")) {
            throw FailedToUpdateIPv6AddressException()
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(super.toString())
        stringBuilder.append(", token = $token")
        return stringBuilder.toString()
    }
}
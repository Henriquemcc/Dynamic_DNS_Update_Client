package dynamic.dns.update.client.model

import dynamic.dns.update.client.exception.*
import dynamic.dns.update.client.network.internet.protocol.getUnicastIPv4Address
import dynamic.dns.update.client.network.internet.protocol.getUnicastIPv6Address
import dynamic.dns.update.client.network.internet.protocol.hostAddressFormatted
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.NetworkInterface
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
        val token: String,
        networkInterfacesName: MutableList<String> = mutableListOf()
) : Host(hostname, enableIPv4, enableIPv6, updateDelayTime, networkInterfacesName) {

    /**
     * Duck DNS subdomain name without '.duckdns.org'
     */
    private val subdomainName: String
        get() {

            var duckDnsSubdomainName = hostname.lowercase()
            val positionDotDuckDnsDotOrg = duckDnsSubdomainName.lastIndexOf(".duckdns.org")

            if (positionDotDuckDnsDotOrg >= 0) {
                duckDnsSubdomainName = duckDnsSubdomainName.substring(0, positionDotDuckDnsDotOrg)
            }

            return duckDnsSubdomainName
        }

    /**
     * Network interfaces which were selected to this host.
     */
    private val selectedNetworkInterfaces: List<NetworkInterface>
        get()
        {
            return if (networkInterfacesName.isNotEmpty())
            {
                NetworkInterface.getNetworkInterfaces().toList().filter {
                    networkInterfacesName.contains(it.name)
                }
            } else
            {
                NetworkInterface.getNetworkInterfaces().toList()
            }
        }

    override fun performIpUpdate(looping: Boolean) {

        val threadIpv4 = object : Thread() {
            override fun run() {
                var success = false
                while (!success) {
                    success = try {
                        performUpdateIPv4(selectedNetworkInterfaces)
                        true
                    } catch (e: Exception) {
                        e.printStackTrace()
                        sleep(Duration.ofMinutes(1).toMillis())
                        false
                    }
                }
            }
        }

        val threadIpv6 = object : Thread() {
            override fun run() {
                var success = false
                while (!success) {
                    success = try {
                        performUpdateIPv6(selectedNetworkInterfaces)
                        true
                    } catch (e: Exception) {
                        e.printStackTrace()
                        sleep(Duration.ofMinutes(1).toMillis())
                        false
                    }
                }
            }
        }

        do {
            if (enableIPv4)
                threadIpv4.run()

            if (enableIPv6)
                threadIpv6.run()

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
    private fun performUpdateIPv4(
            networkInterfaces: List<NetworkInterface> =
                    NetworkInterface.getNetworkInterfaces().toList()
    ) {
        val ipv4Address =
                getUnicastIPv4Address(networkInterfaces)?.hostAddressFormatted ?: throw IPv4NotFoundException()

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
    private fun performUpdateIPv6(
            networkInterfaces: List<NetworkInterface> =
                    NetworkInterface.getNetworkInterfaces().toList()
    ) {
        val ipv6Address =
                getUnicastIPv6Address(networkInterfaces)?.hostAddressFormatted ?: throw IPv6NotFoundException()

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
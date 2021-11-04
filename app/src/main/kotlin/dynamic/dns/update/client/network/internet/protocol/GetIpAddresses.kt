package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.collections.ArrayList

/**
 * Retrieves a list of IP addresses.
 * @param networkInterfaces Network interfaces to retrieve IP addresses.
 * @return List of IP addresses.
 */
fun getIPAddresses(networkInterfaces: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()):
        List<InetAddress> {

    val ipAddresses = ArrayList<InetAddress>()

    for (networkInterface in networkInterfaces) {
        for (inetAddress in networkInterface.inetAddresses) {
            ipAddresses.add(inetAddress)
        }
    }
    return ipAddresses.toList()
}
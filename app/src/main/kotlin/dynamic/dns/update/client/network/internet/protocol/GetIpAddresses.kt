package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress
import java.net.NetworkInterface

/**
 * Retrieves a list of IP addresses.
 * @param networkInterfaces Network interfaces to retrieve IP addresses.
 * @return List of IP addresses.
 */
fun getIPAddresses(networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()):
        List<InetAddress> {

    val ipAddresses = ArrayList<InetAddress>()

    for (networkInterface in networkInterfaces) {
        for (inetAddress in networkInterface.inetAddresses) {
            ipAddresses.add(inetAddress)
        }
    }
    return ipAddresses.toList()
}
package dynamic.dns.update.client.network.internet.protocol

import java.net.Inet6Address
import java.net.NetworkInterface

/**
 * Retrieves an unicast IPv6 address.
 * @param networkInterfaces Network interfaces to retrieve IP addresses.
 * @return An unicast IPv6 address.
 */
fun getUnicastIPv6Address(networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()):
        Inet6Address? {

    val addresses = getIPAddresses(networkInterfaces)
    for (address in addresses) {
        if ((address is Inet6Address) && (address.isUnicastAddress)) {
            return address
        }
    }
    return null
}
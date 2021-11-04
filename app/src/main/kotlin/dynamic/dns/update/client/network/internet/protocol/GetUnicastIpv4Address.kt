package dynamic.dns.update.client.network.internet.protocol

import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*

/**
 * Retrieves an unicast IPv4 address.
 * @param networkInterfaces Network interfaces to retrieve IP addresses.
 * @return An unicast IPv4 address.
 */
fun getUnicastIPv4Address(networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()):
        Inet4Address? {

    val addresses = getIPAddresses(networkInterfaces)
    for (address in addresses) {
        if ((address is Inet4Address) && (address.isUnicastAddress)) {
            return address
        }
    }
    return getUnicastIPv4AddressFromServer()
}
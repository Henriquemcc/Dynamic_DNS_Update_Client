package dynamic.dns.update.client.network.internet.protocol

import java.net.Inet4Address

/**
 * Retrieves an unicast IPv4 address.
 * @return An unicast IPv4 address.
 */
fun getUnicastIPv4Address(): Inet4Address? {
    val addresses = getIPAddresses()
    for (address in addresses)
        if ((address is Inet4Address) &&
            (address.isUnicastAddress)
        )
            return address
    return getUnicastIPv4AddressFromServer()
}
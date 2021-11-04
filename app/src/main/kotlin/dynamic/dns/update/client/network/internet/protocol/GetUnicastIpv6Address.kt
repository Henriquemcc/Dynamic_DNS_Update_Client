package dynamic.dns.update.client.network.internet.protocol

import java.net.Inet6Address

/**
 * Retrieves an unicast IPv6 address.
 * @return An unicast IPv6 address.
 */
fun getUnicastIPv6Address(): Inet6Address? {
    val addresses = getIPAddresses()
    for (address in addresses)
        if ((address is Inet6Address) &&
            (address.isUnicastAddress)
        )
            return address
    return null
}
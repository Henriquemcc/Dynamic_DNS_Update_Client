package dynamic.dns.update.client.network.internet.protocol

import java.net.Inet4Address

fun getUnicastIPv4Address(): Inet4Address? {
    val addresses = getIPAddresses()
    for (address in addresses)
        if ((address is Inet4Address) &&
            (address.isUnicastAddress)
        )
            return address
    return getUnicastIPv4AddressFromServer()
}
package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress
import java.net.NetworkInterface

fun getIPAddresses(): List<InetAddress> {
    val ipAddresses = ArrayList<InetAddress>()
    for (networkInterface in NetworkInterface.getNetworkInterfaces())
        for (inetAddress in networkInterface.inetAddresses)
            ipAddresses.add(inetAddress)
    return ipAddresses.toList()
}
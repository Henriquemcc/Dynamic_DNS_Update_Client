package io.github.henriquemcc.dduc.service

import java.net.*
import java.util.*
import javax.net.ssl.HttpsURLConnection

class NetworkServiceImpl: NetworkService {

    private fun getNetworkInterfaces(networkInterfaceNames: List<String>): List<NetworkInterface> {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).toList().filter { iface -> networkInterfaceNames.contains(iface.name) }
    }

    private fun isUnicastAddress(address: InetAddress): Boolean {
        return !address.isMulticastAddress && !address.isLinkLocalAddress &&
                !address.isSiteLocalAddress && !address.isLoopbackAddress &&
                !address.isAnyLocalAddress
    }

    private fun getIPAddressesFromInterfaces(networkInterfaces: List<NetworkInterface> = NetworkInterface.getNetworkInterfaces().toList()):
            List<InetAddress> {

        val ipAddresses = ArrayList<InetAddress>()

        for (networkInterface in networkInterfaces) {
            for (inetAddress in networkInterface.inetAddresses) {
                ipAddresses.add(inetAddress)
            }
        }
        return ipAddresses.toList()
    }

    private fun getPublicIpv4AddressFromInterfaces(networkInterfaceNames: List<String>): Inet4Address? {
        val interfaces = getNetworkInterfaces(networkInterfaceNames)
        val addresses = getIPAddressesFromInterfaces(interfaces)
        for (address in addresses) {
            if (address is Inet4Address && isUnicastAddress(address)) {
                return address
            }
        }
        return null
    }

    private fun getPublicIpv6AddressFromInterfaces(networkInterfaceNames: List<String>): Inet6Address? {
        val interfaces = getNetworkInterfaces(networkInterfaceNames)
        val addresses = getIPAddressesFromInterfaces(interfaces)
        for (address in addresses) {
            if (address is Inet6Address && isUnicastAddress(address)) {
                return address
            }
        }
        return null
    }

    private fun getPublicIpFromService(urlStr: String): String? {
        return try {
            val url = URL(urlStr)
            val connection = url.openConnection() as HttpsURLConnection

            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            connection.inputStream.bufferedReader().use { reader ->
                reader.readLine()
            }
        } catch (e: Exception) {
            println("ERROR: Failed to obtain IP address: ${e.message}")
            null
        }
    }

    private fun getPublicIpv4AddressFromService(): Inet4Address? {
        val ipAddress = getPublicIpFromService("https://api4.ipify.org")
        if (ipAddress != null) {
            return Inet4Address.getByName(ipAddress) as Inet4Address?
        }
        return null
    }

    private fun getPublicIpv6AddressFromService(): Inet6Address? {
        val ipAddress = getPublicIpFromService("https://api6.ipify.org")
        if (ipAddress != null) {
            return Inet6Address.getByName(ipAddress) as Inet6Address?
        }
        return null
    }

    override fun getPublicIpv4Address(networkInterfaceNames: List<String>): Inet4Address? {
        return getPublicIpv4AddressFromInterfaces(networkInterfaceNames) ?: getPublicIpv4AddressFromService()

    }

    override fun getPublicIpv6Address(networkInterfaceNames: List<String>): Inet6Address? {
        return (getPublicIpv6AddressFromInterfaces(networkInterfaceNames) ?: getPublicIpv6AddressFromService())
    }
}
package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress

val InetAddress.hostAddressFormatted: String
    get() {
        var ipAddress = this.hostAddress
        if (ipAddress.contains("%"))
            ipAddress = ipAddress.substring(0, ipAddress.indexOf("%"))
        return ipAddress
    }
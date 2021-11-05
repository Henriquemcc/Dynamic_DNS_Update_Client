package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress

/**
 * A formatted IP string from a InetAddress.
 */
val InetAddress.hostAddressFormatted: String
    get() {
        var ipAddress = this.hostAddress
        if (ipAddress.contains("%"))
            ipAddress = ipAddress.substring(0, ipAddress.indexOf("%"))
        return ipAddress
    }
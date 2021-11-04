package dynamic.dns.update.client.network.internet.protocol

import java.net.InetAddress

/**
 * Whether a InetAddress is unicast.
 */
val InetAddress.isUnicastAddress: Boolean
    get() {
        return !this.isMulticastAddress && !this.isLinkLocalAddress &&
                !this.isSiteLocalAddress && !this.isLoopbackAddress &&
                !this.isAnyLocalAddress
    }
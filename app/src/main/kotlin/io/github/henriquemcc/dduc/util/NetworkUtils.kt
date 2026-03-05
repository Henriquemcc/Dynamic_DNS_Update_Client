package io.github.henriquemcc.dduc.util

import java.net.InetAddress

fun formatHostAddress(inetAddress: InetAddress): String {
    return inetAddress.hostAddress.substringBefore('%')
}
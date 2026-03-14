package io.github.henriquemcc.dduc.service

import java.net.Inet4Address
import java.net.Inet6Address

interface NetworkService {
    fun getPublicIpv4Address(networkInterfaceNames: List<String>): Inet4Address?
    fun getPublicIpv6Address(networkInterfaceNames: List<String>): Inet6Address?
}
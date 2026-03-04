package io.github.henriquemcc.dduc.provider

import io.github.henriquemcc.dduc.model.DynamicDns
import java.net.Inet4Address
import java.net.Inet6Address

interface DynamicDnsProvider {
    fun updateIpAddress(dynamicDns: DynamicDns, ipv4Address: Inet4Address?, ipv6Address: Inet6Address?): Boolean
    fun cleanIpAddress(dynamicDns: DynamicDns): Boolean
    fun testAuthentication(dynamicDns: DynamicDns): Boolean
    fun getType(): String
}
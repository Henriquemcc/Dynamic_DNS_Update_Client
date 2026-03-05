package io.github.henriquemcc.dduc.service

import io.github.henriquemcc.dduc.model.DynamicDns
import io.github.henriquemcc.dduc.provider.DynamicDnsProvider

class DynamicDnsServiceImpl(
    dynamicDnsProviders: List<DynamicDnsProvider>,
    private val networkService: NetworkService
): DynamicDnsService {

    private val providerMap = dynamicDnsProviders.associateBy { it.getType() }

    override fun updateIpAddress(dynamicDns: DynamicDns): Boolean {
        println("INFO: Updating IP address for ${dynamicDns.domain}")

        val provider = providerMap[dynamicDns.type]
        if (provider == null) {
            println("ERROR: No provider found for type: ${dynamicDns.type}")
            return false
        }

        val ipv4 = networkService.getPublicIpv4Address(dynamicDns.networkInterfaceNames)
        val ipv6 = networkService.getPublicIpv6Address(dynamicDns.networkInterfaceNames)
        provider.updateIpAddress(dynamicDns, ipv4, ipv6)

        println("INFO: IP address updated for ${dynamicDns.domain}")
        return true
    }

    override fun cleanIpAddress(dynamicDns: DynamicDns): Boolean {
        val provider = providerMap[dynamicDns.type]
        if (provider == null) {
            println("ERROR: No provider found for type: ${dynamicDns.type}")
            return false
        }
        return provider.cleanIpAddress(dynamicDns)
    }

    override fun testAuthentication(dynamicDns: DynamicDns): Boolean {
        val provider = providerMap[dynamicDns.type]
        if (provider == null) {
            println("ERROR: No provider found for type: ${dynamicDns.type}")
            return false
        }
        return provider.testAuthentication(dynamicDns)
    }
}
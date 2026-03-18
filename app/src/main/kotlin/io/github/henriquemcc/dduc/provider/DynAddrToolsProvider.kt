package io.github.henriquemcc.dduc.provider

import io.github.henriquemcc.dduc.model.DynAddrToolsDynamicDns
import io.github.henriquemcc.dduc.model.DynamicDns
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DynAddrToolsProvider: DynamicDnsProvider {
    override fun updateIpAddress(
        dynamicDns: DynamicDns,
        ipv4Address: Inet4Address?,
        ipv6Address: Inet6Address?
    ): Boolean {
        val dynAddrToolsDynamicDns = dynamicDns as DynAddrToolsDynamicDns
        var success = false

        if (dynamicDns.enableIpv4 && ipv4Address != null) {
            val urlStringIpv4 = StringBuilder("ipv4.dyn.addr.tools/?")
                .append("secret=${dynAddrToolsDynamicDns.secret}")
                .append("ip=${ipv4Address}")
            success = openConnection(urlStringIpv4, dynamicDns)
        }

        if (dynamicDns.enableIpv6 && ipv6Address != null) {
            val urlStringIpv6 = StringBuilder("ipv6.dyn.addr.tools/?")
                .append("secret=${dynAddrToolsDynamicDns.secret}")
                .append("ip=${ipv6Address}")
            success = (openConnection(urlStringIpv6, dynamicDns) && success)
        }

        return success
    }

    override fun cleanIpAddress(dynamicDns: DynamicDns): Boolean {
        val success = updateIpAddress(dynamicDns, Inet4Address.getByName("0.0.0.0") as Inet4Address?,
            Inet6Address.getByName("::0") as Inet6Address?
        )

        if (success) {
            println("INFO: Successfully cleaned the IP address of ${dynamicDns.domain} with ${dynamicDns.type} provider.")
        }

        return success
    }

    override fun testAuthentication(dynamicDns: DynamicDns): Boolean {
        val dynAddrToolsDynamicDns = dynamicDns as DynAddrToolsDynamicDns
        val urlString = StringBuilder("ipv4.dyn.addr.tools/?")
            .append("secret=${dynAddrToolsDynamicDns.secret}")

        val success = openConnection(urlString, dynamicDns)

        if (success) {
            println("INFO: Successfully authenticated the domain/subdomain ${dynamicDns.domain} with ${dynamicDns.type} provider.")
        }

        return success
    }

    override fun getType(): String {
        return "DynAddrTools"
    }

    private fun openConnection(
        urlString: java.lang.StringBuilder?,
        dynamicDns: DynamicDns
    ): Boolean {
        return try {
            val connection = URL(urlString.toString()).openConnection() as HttpsURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = "GET"
            val response = connection.getInputStream().bufferedReader().readText()
            response.trim() == "OK"
        } catch (e: Exception) {
            println("ERROR: Failed to update ${dynamicDns.type} ${dynamicDns.domain}: ${e.message}")
            false
        }
    }
}
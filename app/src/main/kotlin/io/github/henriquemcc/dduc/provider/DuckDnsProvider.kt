package io.github.henriquemcc.dduc.provider

import io.github.henriquemcc.dduc.model.DuckDnsDynamicDns
import io.github.henriquemcc.dduc.model.DynamicDns
import io.github.henriquemcc.dduc.util.formatHostAddress
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DuckDnsProvider: DynamicDnsProvider {
    override fun updateIpAddress(
        dynamicDns: DynamicDns,
        ipv4Address: Inet4Address?,
        ipv6Address: Inet6Address?
    ): Boolean {
        val urlString = StringBuilder("https://www.duckdns.org/update?")
            .append("domains=${dynamicDns.domain.substringBefore(".duckdns.org")}")
            .append("&token=${(dynamicDns as DuckDnsDynamicDns).token}")

        if (dynamicDns.enableIpv4 && ipv4Address != null)
            urlString.append("&ip=${formatHostAddress(ipv4Address)}")

        if (dynamicDns.enableIpv6 && ipv6Address != null)
            urlString.append("&ipv6=${formatHostAddress(ipv6Address)}")

        return openConnection(urlString, dynamicDns)
    }

    override fun cleanIpAddress(dynamicDns: DynamicDns): Boolean {
        val urlString = StringBuilder("https://www.duckdns.org/update?")
            .append("domains=${dynamicDns.domain.substringBefore(".duckdns.org")}")
            .append("&token=${(dynamicDns as DuckDnsDynamicDns).token}")
            .append("&clear=true")

        val success = openConnection(urlString, dynamicDns)

        if (success) {
            println("INFO: Successfully cleaned the IP address of ${dynamicDns.domain} with ${dynamicDns.type} provider.")
        }

        return success
    }

    override fun testAuthentication(dynamicDns: DynamicDns): Boolean {
        val urlString = StringBuilder("https://www.duckdns.org/update?")
            .append("domains=${dynamicDns.domain.substringBefore(".duckdns.org")}")
            .append("&token=${(dynamicDns as DuckDnsDynamicDns).token}")

        val success = openConnection(urlString, dynamicDns)

        if (success) {
            println("INFO: Successfully authenticated the subdomain ${dynamicDns.domain} with ${dynamicDns.type} provider.")
        }

        return success
    }

    override fun getType(): String {
        return "DuckDns"
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
package io.github.henriquemcc.dduc.provider

import io.github.henriquemcc.dduc.model.DynamicDns
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
            .append("domains=${dynamicDns.domain}")
            .append("&token=${dynamicDns.token}")

        if (dynamicDns.enableIpv4 && ipv4Address != null)
            urlString.append("&ip=${ipv4Address}")

        if (dynamicDns.enableIpv6 && ipv6Address != null)
            urlString.append("&ipv6=${ipv6Address}")

        return openConnection(urlString, dynamicDns)
    }

    override fun cleanIpAddress(dynamicDns: DynamicDns): Boolean {
        val urlString = StringBuilder("https://www.duckdns.org/update?")
            .append("domains=${dynamicDns.domain}")
            .append("&token=${dynamicDns.token}")
            .append("&clear=true")

        return openConnection(urlString, dynamicDns)
    }

    override fun testAuthentication(dynamicDns: DynamicDns): Boolean {
        val urlString = StringBuilder("https://www.duckdns.org/update?")
            .append("&token=${dynamicDns.token}")

        return openConnection(urlString, dynamicDns)
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
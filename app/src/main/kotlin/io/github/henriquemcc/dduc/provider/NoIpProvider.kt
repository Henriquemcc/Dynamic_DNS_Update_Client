package io.github.henriquemcc.dduc.provider

import io.github.henriquemcc.dduc.model.DynamicDns
import io.github.henriquemcc.dduc.model.NoIpDynamicDns
import io.github.henriquemcc.dduc.util.formatHostAddress
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.URL
import java.util.Properties
import javax.net.ssl.HttpsURLConnection
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class NoIpProvider: DynamicDnsProvider {

    private fun getUserAgent(): String {
        val props = Properties()
        try {
            NoIpProvider::class.java.getResourceAsStream("/app.properties").use { resourceAsStream ->
                props.load(resourceAsStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val company = props.getProperty("company", "UnknownCompany")
        val programName = props.getProperty("program.name")
        val osVersion = System.getProperty("os.version")
        val releaseVersion = NoIpProvider::class.java.getPackage().implementationVersion
        val developerEmail = props.getProperty("developer.email")

        return "$company $programName/$osVersion-$releaseVersion $developerEmail"
    }

    @OptIn(ExperimentalEncodingApi::class)
    override fun updateIpAddress(
        dynamicDns: DynamicDns,
        ipv4Address: Inet4Address?,
        ipv6Address: Inet6Address?
    ): Boolean {
        val urlString = StringBuilder("https://dynupdate.no-ip.com/nic/update?")
            .append("hostname=${dynamicDns.domain}")

        if (dynamicDns.enableIpv4 && ipv4Address != null)
            urlString.append("&myip=${formatHostAddress(ipv4Address)}")

        if (dynamicDns.enableIpv6 && ipv6Address != null)
            urlString.append("&myipv6=${formatHostAddress(ipv6Address)}")

        val noIpDns = dynamicDns as NoIpDynamicDns
        val authString = "${noIpDns.username}:${noIpDns.password}"
        val encodedAuth = Base64.encode(authString.toByteArray())
        val headers = mapOf("Authorization" to "Basic $encodedAuth", "User-Agent" to getUserAgent())
        val response = openConnection(urlString.toString(), headers)
        return response?.trim()?.startsWith("good") == true || response?.trim()?.startsWith("nochg") == true
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
        val success =  updateIpAddress(dynamicDns, null, null)

        if (success) {
            println("INFO: Successfully authenticated the domain/subdomain ${dynamicDns.domain} with ${dynamicDns.type} provider.")
        }

        return success
    }

    override fun getType(): String {
        return "NoIp"
    }

    private fun openConnection(urlString: String, headers: Map<String, String>): String? {
        return try {
            val connection = URL(urlString).openConnection() as HttpsURLConnection
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = "GET"
            headers.forEach { (key, value) -> connection.setRequestProperty(key, value) }

            connection.inputStream.bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            println("ERROR: Failed to connect to No-IP: ${e.message}")
            null
        }
    }
}
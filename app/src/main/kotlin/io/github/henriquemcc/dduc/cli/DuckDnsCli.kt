package io.github.henriquemcc.dduc.cli

import io.github.henriquemcc.dduc.model.DuckDnsDynamicDns
import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.service.DynamicDnsService
import java.io.File

class DuckDnsCli(
    private val repository: DynamicDnsRepository,
    private val service: DynamicDnsService
    ): DynamicDnsCli {

    override fun add(args: Array<String>) {
        val type = args[1]
        val domain = args[2]
        val token = args[3]

        val enableIpv4 = args.getOrNull(4)?.toBooleanStrictOrNull() ?: true
        val enableIpv6 = args.getOrNull(5)?.toBooleanStrictOrNull() ?: true
        val updateDelayTime = args.getOrNull(6)?.toLongOrNull() ?: 300000L // Default 5 minutes
        val retryDelayTime = args.getOrNull(7)?.toLongOrNull() ?: 60000L // Default 1 minute
        val networkInterfaceNames = args.getOrNull(8)?.split(",") ?: emptyList()

        val dynamicDns = DuckDnsDynamicDns(
            type = type,
            domain = domain,
            enableIpv4 = enableIpv4,
            enableIpv6 = enableIpv6,
            updateDelayTime = updateDelayTime,
            retryDelayTime = retryDelayTime,
            token = token,
            networkInterfaceNames = networkInterfaceNames
        )

        repository.save(dynamicDns)
        println("INFO: Dynamic DNS entry for $domain added successfully.")
    }

    override fun alter(args: Array<String>) {
        val domain = args[2]
        val attribute = args[3]
        val value = args[4]

        val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns

        val updatedDynamicDns = dynamicDns.clone()
        when (attribute) {
            "enableIpv4" -> updatedDynamicDns.enableIpv4 = value.toBooleanStrict()
            "enableIpv6" -> updatedDynamicDns.enableIpv6 = value.toBooleanStrict()
            "updateDelayTime" -> updatedDynamicDns.updateDelayTime = value.toLong()
            "retryDelayTime" -> updatedDynamicDns.retryDelayTime = value.toLong()
            "token" -> updatedDynamicDns.token = value
            "networkInterfaceName" -> updatedDynamicDns.networkInterfaceNames = value.split(",")
            else -> {
                println("Unknown attribute: $attribute")
                return
            }
        }

        repository.delete(domain)
        repository.save(updatedDynamicDns)
        println("INFO: Dynamic DNS entry for $domain updated successfully.")
    }

    override fun delete(args: Array<String>) {
        val domain = args[2]
        repository.delete(domain)
        println("INFO: Dynamic DNS entry for $domain deleted successfully.")
    }

    override fun help(args: Array<String>) {
        println("Usage: dduc <command> [options]")
        println()
        println("Commands:")
        println("dduc help DuckDns\n\t\t\t\t\t\t\t\tShows this help menu.")
        println("dduc add DuckDns <subdomain> <token> [<enableIpv4> <enableIpv6> <updateDelayTime> <retryDelayTime>]\n\t\t\t\t\t\t\t\tAdds a new DuckDns subdomain.")
        println("dduc list DuckDns\t\t\t\t\t\tLists all DuckDns subdomains.")
        println("dduc alter DuckDns <subdomain> <attribute> <value>\t\tAlters the value of a DuckDns subdomain attribute.")
        println("dduc delete DuckDns <subdomain>\t\t\t\t\tDeletes a DuckDns subdomain.")
        println("dduc force-update DuckDns [<subdomain>]\t\t\t\tForces the update of the IP address of a DuckDns subdomain, ignoring the updateDelayTime.")
        println("dduc force-clean DuckDns [<subdomain>]\t\t\t\tForces the cleaning of the IP address of a DuckDns subdomain.")
        println("dduc test-auth DuckDns [<subdomain>]\t\t\t\tTests the authentication with DuckDns.")
    }

    override fun testAuth(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DuckDns").forEach { service.testAuthentication(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.testAuthentication(dynamicDns)
        }
    }

    override fun forceClean(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DuckDns").forEach { service.cleanIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.cleanIpAddress(dynamicDns)
        }
    }

    override fun forceUpdate(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DuckDns").forEach { service.updateIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.updateIpAddress(dynamicDns)
        }
    }

    override fun getType(): String {
        return "DuckDns"
    }
}
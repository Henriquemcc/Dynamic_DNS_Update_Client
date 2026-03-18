package io.github.henriquemcc.dduc.cli

import io.github.henriquemcc.dduc.model.DynAddrToolsDynamicDns
import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.service.DynamicDnsService

class DynAddrToolsCli(
    private val repository: DynamicDnsRepository,
    private val service: DynamicDnsService
): DynamicDnsCli {
    override fun add(args: Array<String>) {
        val type = args[1]
        val secret = args[2]

        val enableIpv4 = args.getOrNull(3)?.toBooleanStrictOrNull() ?: true
        val enableIpv6 = args.getOrNull(4)?.toBooleanStrictOrNull() ?: true
        val updateDelayTime = args.getOrNull(5)?.toLongOrNull() ?: 300000L // Default 5 minutes
        val retryDelayTime = args.getOrNull(6)?.toLongOrNull() ?: 60000L // Default 1 minute
        val networkInterfaceNames = args.getOrNull(7)?.split(",") ?: emptyList()

        val dynamicDns = DynAddrToolsDynamicDns(
            type = type,
            enableIpv4 = enableIpv4,
            enableIpv6 = enableIpv6,
            updateDelayTime = updateDelayTime,
            retryDelayTime = retryDelayTime,
            networkInterfaceNames = networkInterfaceNames,
            secret = secret
        )

        repository.save(dynamicDns)
        println("INFO: Dynamic DNS entry for ${dynamicDns.domain} added successfully.")
    }

    override fun alter(args: Array<String>) {
        val domain = args[2]
        val attribute = args[3]
        val value = args[4]

        val dynamicDns = repository.findByDomain(domain) as DynAddrToolsDynamicDns
        
        val updatedDynamicDns = dynamicDns.clone()
        when (attribute) {
            "enableIpv4" -> updatedDynamicDns.enableIpv4 = value.toBooleanStrict()
            "enableIpv6" -> updatedDynamicDns.enableIpv6 = value.toBooleanStrict()
            "updateDelayTime" -> updatedDynamicDns.updateDelayTime = value.toLong()
            "retryDelayTime" -> updatedDynamicDns.retryDelayTime = value.toLong()
            "secret" -> updatedDynamicDns.secret = value
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
        println("dduc help DynAddrTools\n\t\t\t\t\t\t\t\tShows this help menu.")
        println("dduc add DynAddrTools <secret> [<enableIpv4> <enableIpv6> <updateDelayTime> <retryDelayTime>]\n\t\t\t\t\t\t\t\tAdds a new DynAddrTools subdomain.")
        println("dduc list DynAddrTools\t\t\t\t\t\tLists all DynAddrTools subdomains.")
        println("dduc alter DynAddrTools <subdomain> <attribute> <value>\t\tAlters the value of a DynAddrTools subdomain attribute.")
        println("dduc delete DynAddrTools <subdomain>\t\t\t\tDeletes a DynAddrTools subdomain.")
        println("dduc force-update DynAddrTools [<subdomain>]\t\t\tForces the update of the IP address of a DynAddrTools subdomain, ignoring the updateDelayTime.")
        println("dduc force-clean DynAddrTools [<subdomain>]\t\t\tForces the cleaning of the IP address of a DynAddrTools subdomain.")
        println("dduc test-auth DynAddrTools [<subdomain>]\t\t\tTests the authentication with DynAddrTools.")
    }

    override fun testAuth(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DynAddrTools").forEach { service.testAuthentication(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DynAddrToolsDynamicDns
            service.testAuthentication(dynamicDns)
        }
    }

    override fun forceClean(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DynAddrTools").forEach { service.cleanIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DynAddrToolsDynamicDns
            service.cleanIpAddress(dynamicDns)
        }
    }

    override fun forceUpdate(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("DynAddrTools").forEach { service.updateIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DynAddrToolsDynamicDns
            service.updateIpAddress(dynamicDns)
        }
    }

    override fun getType(): String {
        return "DynAddrTools"
    }
}
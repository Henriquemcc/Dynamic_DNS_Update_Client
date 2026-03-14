package io.github.henriquemcc.dduc.cli

import io.github.henriquemcc.dduc.model.DuckDnsDynamicDns
import io.github.henriquemcc.dduc.model.NoIpDynamicDns
import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.service.DynamicDnsService
import java.io.File

class NoIpCli(
    private val repository: DynamicDnsRepository,
    private val service: DynamicDnsService
): DynamicDnsCli {
    private val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name

    override fun add(args: Array<String>) {
        val type = args[1]
        val domain = args[2]
        val username = args[3]
        val password = args[4]

        val enableIpv4 = args.getOrNull(5)?.toBooleanStrictOrNull() ?: true
        val enableIpv6 = args.getOrNull(6)?.toBooleanStrictOrNull() ?: true
        val updateDelayTime = args.getOrNull(7)?.toLongOrNull() ?: 300000L // Default 5 minutes
        val retryDelayTime = args.getOrNull(8)?.toLongOrNull() ?: 60000L // Default 1 minute
        val networkInterfaceNames = args.getOrNull(9)?.split(",") ?: emptyList()


        val dynamicDns = NoIpDynamicDns(
            type = type,
            domain = domain,
            enableIpv4 = enableIpv4,
            enableIpv6 = enableIpv6,
            updateDelayTime = updateDelayTime,
            retryDelayTime = retryDelayTime,
            networkInterfaceNames = networkInterfaceNames,
            username = username,
            password = password
        )

        repository.save(dynamicDns)
        println("INFO: Dynamic DNS entry for $domain added successfully.")
    }

    override fun alter(args: Array<String>) {
        val domain = args[2]
        val attribute = args[3]
        val value = args[4]

        val dynamicDns = repository.findByDomain(domain) as NoIpDynamicDns

        val updatedDynamicDns = dynamicDns.clone()
        when (attribute) {
            "enableIpv4" -> updatedDynamicDns.enableIpv4 = value.toBooleanStrict()
            "enableIpv6" -> updatedDynamicDns.enableIpv6 = value.toBooleanStrict()
            "updateDelayTime" -> updatedDynamicDns.updateDelayTime = value.toLong()
            "retryDelayTime" -> updatedDynamicDns.retryDelayTime = value.toLong()
            "username" -> updatedDynamicDns.username = value
            "password" -> updatedDynamicDns.password = value
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
        println("dduc help NoIp\n\t\t\t\t\t\t\tShows this help menu.")
        println("dduc add NoIp <domain> <username> <password> [<enableIpv4> <enableIpv6> <updateDelayTime> <retryDelayTime>]\n\t\t\t\t\t\t\tAdds a new NoIp domain.")
        println("dduc list NoIp\t\t\t\t\t\tLists all NoIp domains.")
        println("dduc alter NoIp <domain> <attribute> <value>\t\tAlters the value of a NoIp domain attribute.")
        println("dduc delete NoIp <domain>\t\t\t\tDeletes a NoIp domain.")
        println("dduc force-update NoIp [<domain>]\t\t\tForces the update of the IP address of a NoIp domain, ignoring the updateDelayTime.")
        println("dduc force-clean NoIp [<domain>]\t\t\tForces the cleaning of the IP address of a NoIp domain.")
        println("dduc test-auth NoIp [<domain>]\t\t\t\tTests the authentication with NoIp.")
    }

    override fun testAuth(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("NoIp").forEach { service.testAuthentication(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.testAuthentication(dynamicDns)
        }
    }

    override fun forceClean(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("NoIp").forEach { service.cleanIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.cleanIpAddress(dynamicDns)
        }
    }

    override fun forceUpdate(args: Array<String>) {
        if (args.size < 3) {
            repository.findByType("NoIp").forEach { service.updateIpAddress(it) }
        } else {
            val domain = args[2]
            val dynamicDns = repository.findByDomain(domain) as DuckDnsDynamicDns
            service.updateIpAddress(dynamicDns)
        }
    }

    override fun getType(): String {
        return "NoIp"
    }
}
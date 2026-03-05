package io.github.henriquemcc.dduc.cli

import io.github.henriquemcc.dduc.model.DynamicDns
import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.service.DynamicDnsService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.Duration

class Cli: KoinComponent {
    private val repository: DynamicDnsRepository by inject()
    private val service: DynamicDnsService by inject()

    fun run(args: Array<String>) {
        if (args.isEmpty()) {
            println("Usage: dduc <command> [options]")
            return
        }

        val command = args[0]
        when (command) {
            "add" -> add(args)
            "list" -> list()
            "alter" -> alter(args)
            "delete" -> delete(args)
            "force-update" -> forceUpdate(args)
            "force-clean" -> forceClean(args)
            "test-auth" -> testAuth(args)
            "daemon" -> daemon()
            else -> println("Unknown command: $command")
        }
    }

    private fun delete(args: Array<String>) {
        if (args.size < 3) {
            println("Usage: dduc delete <type> <domain/subdomain>")
            return
        }

        val domain = args[2]
        repository.delete(domain)
        println("INFO: Dynamic DNS entry for $domain deleted successfully.")
    }

    private fun alter(args: Array<String>) {
        if (args.size < 5) {
            println("Usage: dduc alter <type> <domain/subdomain> <attribute> <value>")
            return
        }

        val domain = args[2]
        val attribute = args[3]
        val value = args[4]

        val dynamicDns = repository.findByDomain(domain)
        if (dynamicDns == null) {
            println("Dynamic DNS entry for $domain not found.")
            return
        }

        val updatedDynamicDns = when (attribute) {
            "enableIpv4" -> dynamicDns.copy(enableIpv4 = value.toBooleanStrict())
            "enableIpv6" -> dynamicDns.copy(enableIpv6 = value.toBooleanStrict())
            "updateDelayTime" -> dynamicDns.copy(updateDelayTime = Duration.parse(value))
            "retryDelayTime" -> dynamicDns.copy(retryDelayTime = Duration.parse(value))
            "token" -> dynamicDns.copy(token = value)
            "networkInterfaceName" -> dynamicDns.copy(networkInterfaceNames = value.split(","))
            else -> {
                println("Unknown attribute: $attribute")
                return
            }
        }

        repository.delete(domain)
        repository.save(updatedDynamicDns)
        println("Dynamic DNS entry for $domain updated successfully.")
    }

    private fun list() {
        val dynamicDnsList = repository.findAll()
        if (dynamicDnsList.isEmpty()) {
            println("INFO: No dynamic DNS entries found.")
            return
        }
        dynamicDnsList.forEach {
            println(it)
        }
    }

    private fun add(args: Array<String>) {
        if (args.size < 4) {
            println("Usage: dduc add <type> <domain/subdomain> <token> [<enableIpv4> <enableIpv6> <delayTime> <retryDelayTime> <networkInterfaceName>]")
            return
        }

        val type = args[1]
        val domain = args[2]
        val token = args[3]

        val enableIpv4 = args.getOrNull(4)?.toBooleanStrictOrNull() ?: true
        val enableIpv6 = args.getOrNull(5)?.toBooleanStrictOrNull() ?: true
        val updateDelayTime = Duration.parse(args[6])
        val retryDelayTime = Duration.parse(args[7])
        val networkInterfaceNames = args.getOrNull(8)?.split(",") ?: emptyList()

        val dynamicDns = DynamicDns(
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

    private fun forceUpdate(args: Array<String>) {
        val domain = args.getOrNull(2)
        if (domain != null) {
            val dynamicDns = repository.findByDomain(domain)
            if (dynamicDns != null) {
                service.updateIpAddress(dynamicDns)
            } else {
                println("ERROR: Dynamic DNS entry for $domain not found.")
            }
        } else {
            repository.findAll().forEach { service.updateIpAddress(it) }
        }
    }

    private fun forceClean(args: Array<String>) {
        val domain = args.getOrNull(2)
        if (domain != null) {
            val dynamicDns = repository.findByDomain(domain)
            if (dynamicDns != null) {
                service.cleanIpAddress(dynamicDns)
            } else {
                println("ERROR: Dynamic DNS entry for $domain not found.")
            }
        } else {
            repository.findAll().forEach { service.cleanIpAddress(it) }
        }
    }

    private fun testAuth(args: Array<String>) {
        val domain = args.getOrNull(2)
        if (domain != null) {
            val dynamicDns = repository.findByDomain(domain)
            if (dynamicDns != null) {
                service.testAuthentication(dynamicDns)
            } else {
                println("ERROR: Dynamic DNS entry for $domain not found.")
            }
        } else {
            repository.findAll().forEach { service.testAuthentication(it) }
        }
    }

    private fun daemon() {
        println("INFO: Running as a daemon...")
        while (true) {
            repository.findAll().forEach {
                service.updateIpAddress(it)
                Thread.sleep(it.updateDelayTime.toMillis())
            }
        }
    }
}
package io.github.henriquemcc.dduc.cli

import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.service.DynamicDnsService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class Cli : KoinComponent {
    private val repository: DynamicDnsRepository by inject()
    private val service: DynamicDnsService by inject()
    private val dynamicDnsCli: List<DynamicDnsCli> by inject()

    fun run(args: Array<String>) {
        if (args.isEmpty()) {
            println("Usage: dduc <command> [options]")
            return
        }

        val command = args[0]
        when (command) {
            "add" -> add(args)
            "list" -> list(args)
            "alter" -> alter(args)
            "delete" -> delete(args)
            "force-update" -> forceUpdate(args)
            "force-clean" -> forceClean(args)
            "test-auth" -> testAuth(args)
            "daemon" -> daemon()
            "help" -> help(args)
            "-h" -> help(args)
            "--help" -> help(args)
            "/?" -> help(args)
            else -> println("Unknown command: $command")
        }
    }

    private fun add(args: Array<String>) {
        if (args.size < 4) {
            println("Usage: dduc add <type> <domain/subdomain> <...> [<enableIpv4> <enableIpv6> <delayTime> <retryDelayTime> <networkInterfaceName>]")
            return
        }

        val dynamicDnsCli = getDynamicDnsCli(args)
        dynamicDnsCli?.add(args)
    }

    private fun list(args: Array<String>) {
        val dynamicDnsList = if (args.size < 2) {
            repository.findAll()
        } else {
            val type = args[1]
            repository.findByType(type)
        }

        if (dynamicDnsList.isEmpty()) {
            println("INFO: No dynamic DNS entries found.")
            return
        }
        dynamicDnsList.forEach {
            println(it)
        }
    }

    private fun alter(args: Array<String>) {
        if (args.size < 5) {
            println("Usage: dduc alter <type> <domain/subdomain> <attribute> <value>")
            return
        }

        val dynamicDnsCli = getDynamicDnsCli(args)
        dynamicDnsCli?.alter(args)
    }

    private fun delete(args: Array<String>) {
        if (args.size < 3) {
            println("Usage: dduc delete <type> <domain/subdomain>")
            return
        }

        val dynamicDnsCli = getDynamicDnsCli(args)
        dynamicDnsCli?.delete(args)
    }

    private fun forceUpdate(args: Array<String>) {
        if (args.size < 2) {
            repository.findAll().forEach { service.updateIpAddress(it) }
        } else {
            val dynamicDnsCli = getDynamicDnsCli(args)
            dynamicDnsCli?.forceUpdate(args)
        }
    }

    private fun forceClean(args: Array<String>) {
        if (args.size < 2) {
            repository.findAll().forEach { service.cleanIpAddress(it) }
        } else {
            val dynamicDnsCli = getDynamicDnsCli(args)
            dynamicDnsCli?.forceClean(args)
        }
    }

    private fun testAuth(args: Array<String>) {
        if (args.size < 2) {
            repository.findAll().forEach { service.testAuthentication(it) }
        } else {
            val dynamicDnsCli = getDynamicDnsCli(args)
            dynamicDnsCli?.testAuth(args)
        }
    }

    private fun daemon() {
        println("INFO: Running as a daemon...")
        repository.findAll().forEach {
            val thread = object : Thread() {
                override fun run() {
                    while (true) {
                        service.updateIpAddress(it)
                        sleep(it.updateDelayTime)
                    }
                }
            }
            thread.start()
        }
    }

    private fun help(args: Array<String>) {
        if (args.size < 2) {
            println("Usage: dduc <command> [options]")
            println()
            println("Commands:")
            println("dduc help <type>\t\t\t\t\t\tShows the help menu for a specific type of dynamic DNS.")
            println("dduc add <type> <domain/subdomain> <...> [<enableIpv4> <enableIpv6> <updateDelayTime> <retryDelayTime>]\n\t\t\t\t\t\t\t\tAdds a new domain/subdomain.")
            println("dduc list [<type>]\t\t\t\t\t\tLists all subdomains.")
            println("dduc alter <type> <domain/subdomain> <attribute> <value>\tAlters the value of an attribute.")
            println("dduc delete <type> <domain/subdomain>\t\t\t\tDeletes a domain/subdomain.")
            println("dduc force-update [<type> <domain/subdomain>]\t\t\tForces the update of the IP address of a domain/subdomain, ignoring the updateDelayTime.")
            println("dduc force-clean [<type> <domain/subdomain>]\t\t\tForces the cleaning (set to NULL or 0.0.0.0, ::0) of the IP address of a domain/subdomain.")
            println("dduc test-auth [<type> <domain/subdomain>]\t\t\tTests the authentication with the dynamic dns provider.")
            println("dduc daemon\t\t\t\t\t\t\tRuns as a daemon.")
        } else {
            val dynamicDnsCli = getDynamicDnsCli(args)
            dynamicDnsCli?.help(args)
        }
    }

    private fun getDynamicDnsCli(args: Array<String>): DynamicDnsCli? {
        val type = args[1]
        val dynamicDnsCli = dynamicDnsCli.find { it.getType() == type }
        if (dynamicDnsCli == null) {
            println("ERROR: Unknown type: $type")
            return null
        }
        return dynamicDnsCli
    }
}
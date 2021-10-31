package dynamic.dns.update.client.console.duckdns

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.graphic.duckdns.duckDnsHosts

class DuckDnsHostListSubdomainsMenu : ConsoleMenu("Duck DNS list subdomains menu", listOf(
    ConsoleButton("Print Duck DNS subdomains sorted by index") {
        var index = 0
        for (host in duckDnsHosts) {
            println("${index++} - $host")
        }
    },
    ConsoleButton("Print Duck DNS subdomains sorted by hostname") {
        val hosts = duckDnsHosts.sortedBy { it.hostname }
        for (host in hosts) {
            println(host)
        }
    },
    ConsoleButton("Print a Duck DNS subdomain, selecting it by it's index") {
        if(duckDnsHosts.isNotEmpty()) {
            val selectedHost = readInteger("Index: ", duckDnsHosts.indices)
            println(duckDnsHosts[selectedHost])
        }
    },
    ConsoleButton("Print a Duck DNS subdomain, selecting it by it's hostname") {
        if(duckDnsHosts.isNotEmpty()) {
            val hostname = readString("Hostname: ")
            println(duckDnsHosts.first {
                it.hostname.lowercase() == hostname.lowercase()
            })
        }
    }
))
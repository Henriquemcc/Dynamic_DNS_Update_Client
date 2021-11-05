package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.host.duckdns.addDuckDnsHost

/**
 * Console menu which allows the user to add a host.
 */
internal fun addHost() {

    StaticConsoleMenu("Add Host console menu", listOf(
        ConsoleButton("Add a Duck DNS subdomain") {
            addDuckDnsHost()
        }
    ))
}
package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.host.duckdns.addDuckDnsSubdomainConsoleMenu

/**
 * Console menu which allows the user to add a host.
 */
fun addHostConsoleMenu() {

    StaticConsoleMenu("Add Host console menu", listOf(
        ConsoleButton("Add a Duck DNS subdomain") {
            addDuckDnsSubdomainConsoleMenu()
        }
    ))
}
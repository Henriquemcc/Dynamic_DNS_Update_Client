package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.duckdns.AddDuckDnsSubdomainConsoleMenu

class AddHostConsoleMenu {
    init {
        StaticConsoleMenu("Add Host console menu", listOf(
            ConsoleButton("Add a Duck DNS subdomain") {
                AddDuckDnsSubdomainConsoleMenu()
            }
        ))
    }
}
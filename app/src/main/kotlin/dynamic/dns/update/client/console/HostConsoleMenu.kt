package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleMenu
import dynamic.dns.update.client.console.duckdns.DuckDnsHostMainConsoleMenu

class HostConsoleMenu : ConsoleMenu("Host menu", listOf(
    ConsoleButton("Duck DNS subdomain") {
        DuckDnsHostMainConsoleMenu()
    }
))
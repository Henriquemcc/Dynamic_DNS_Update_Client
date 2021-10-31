package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleMenu

class HostConsoleMenu : ConsoleMenu("Host menu", listOf(
    ConsoleButton("Show hosts") {
        ListHostsConsoleMenu()
    },
    ConsoleButton("Create host") {

    },
    ConsoleButton("Edit host") {

    },
    ConsoleButton("Remove host") {

    }
))
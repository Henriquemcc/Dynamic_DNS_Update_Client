package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleMenu
import dynamic.dns.update.client.controller.HostsController

class MainConsoleMenu : ConsoleMenu("Main menu", listOf(
    ConsoleButton("Host menu") {
        HostConsoleMenu()
    },
    ConsoleButton("Perform host update") {
        HostsController.performUpdate()
    },
    ConsoleButton("Perform host update in infinite looping") {
        HostsController.performUpdate(true)
    }
))
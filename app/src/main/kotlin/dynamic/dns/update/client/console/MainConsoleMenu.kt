package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.controller.HostsController

class MainConsoleMenu {

    init {
        StaticConsoleMenu(
            "Main menu", listOf(
                ConsoleButton("Host menu") {
                    HostConsoleMenu()
                },
                ConsoleButton("Perform host IP cleaning") {
                    HostsController.performIpCleaning()
                },
                ConsoleButton("Perform host IP update") {
                    HostsController.performIpUpdate()
                },
                ConsoleButton("Perform host IP update in infinite looping") {
                    HostsController.performIpUpdate(true)
                })
        )
    }
}
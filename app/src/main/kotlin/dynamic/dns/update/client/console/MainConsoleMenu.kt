package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.host.hostConsoleMenu
import dynamic.dns.update.client.controller.HostsController

/**
 * Console menu which allows the user to select what they want to do.
 */
fun mainConsoleMenu() {

    StaticConsoleMenu(
        "Main menu", listOf(
            ConsoleButton("Host menu") {
                hostConsoleMenu()
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
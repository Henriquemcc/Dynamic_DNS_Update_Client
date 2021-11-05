package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu

/**
 * Console menu which allows the user to manage hosts.
 */
fun hostMainMenu() {

    StaticConsoleMenu("Host menu", listOf(
        ConsoleButton("Show hosts") {
            listHosts()
        },
        ConsoleButton("Add host") {
            addHost()
        },
        ConsoleButton("Edit host") {
            UpdateHost()
        },
        ConsoleButton("Remove host") {
            deleteHost()
        }
    ))
}
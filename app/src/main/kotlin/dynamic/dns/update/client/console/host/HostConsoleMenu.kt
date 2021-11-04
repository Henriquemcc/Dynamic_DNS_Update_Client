package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.UpdateHostMenu
import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu

/**
 * Console menu which allows the user to manage hosts.
 */
fun hostConsoleMenu() {

    StaticConsoleMenu("Host menu", listOf(
        ConsoleButton("Show hosts") {
            listHostsConsoleMenu()
        },
        ConsoleButton("Add host") {
            addHostConsoleMenu()
        },
        ConsoleButton("Edit host") {
            UpdateHostMenu()
        },
        ConsoleButton("Remove host") {
            deleteHostConsoleMenu()
        }
    ))
}
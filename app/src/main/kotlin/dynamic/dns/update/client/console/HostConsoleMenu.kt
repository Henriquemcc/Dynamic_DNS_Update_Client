package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu

/**
 * Console menu which allows the user to manage hosts.
 */
class HostConsoleMenu {

    /**
     * Initialization block.
     */
    init {
        StaticConsoleMenu("Host menu", listOf(
            ConsoleButton("Show hosts") {
                ListHostsConsoleMenu()
            },
            ConsoleButton("Add host") {
                AddHostConsoleMenu()
            },
            ConsoleButton("Edit host") {
                UpdateHostMenu()
            },
            ConsoleButton("Remove host") {
                DeleteHostConsoleMenu()
            }
        ))
    }
}
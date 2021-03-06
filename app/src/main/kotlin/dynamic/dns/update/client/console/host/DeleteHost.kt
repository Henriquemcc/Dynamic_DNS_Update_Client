package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.console.common.getConfirmationConsole
import dynamic.dns.update.client.controller.HostsController

/**
 * Console menu which allows the user to delete a host.
 */
internal fun deleteHost() {

    StaticConsoleMenu("Delete host console menu", listOf(
        ConsoleButton("List hosts") {
            listHosts()
        },
        ConsoleButton("Delete host by it's index") {
            if (HostsController.isNotEmpty()) {
                val index = readInteger("Index: ", HostsController.indices)
                if (getConfirmationConsole("Would you like to delete ${HostsController[index]} ?")) {
                    HostsController.removeAt(index)
                }
            }
        },
        ConsoleButton("Delete host by it's hostname") {
            if (HostsController.isNotEmpty()) {
                val hostname = readString("Hostname: ")
                val host = HostsController.find {
                    it.hostname.lowercase() == hostname.lowercase()
                }
                if (host != null) {
                    if (getConfirmationConsole("Would you like to delete $host ?")) {
                        HostsController.remove(host)
                    }
                }
            }
        }
    ))
}
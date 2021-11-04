package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.console.duckdns.UpdateDuckDnsSubdomainConsoleMenu
import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain
import dynamic.dns.update.client.model.Host

/**
 * Console menu which allows the user to modify a host.
 */
class UpdateHostMenu {

    /**
     * Identify the type of host and redirects the user to the specific menu.
     * @param oldHost Host which will be updated.
     */
    private fun updateHost(oldHost: Host) {
        if (oldHost is DuckDnsSubdomain) {
            UpdateDuckDnsSubdomainConsoleMenu(oldHost)
        }
    }

    /**
     * Initialization block.
     */
    init {
        StaticConsoleMenu("Update host menu", listOf(
            ConsoleButton("List hosts") {
                ListHostsConsoleMenu()
            },
            ConsoleButton("Select a host to update by it's index") {
                if (HostsController.isNotEmpty()) {
                    val index = readInteger("Index: ", HostsController.indices)
                    updateHost(HostsController[index])
                }
            },
            ConsoleButton("Select a host update by it's hostname") {
                if (HostsController.isNotEmpty()) {
                    val hostname = readString("Hostname: ")
                    val host = HostsController.find {
                        it.hostname.lowercase() == hostname.lowercase()
                    }
                    if (host != null) {
                        updateHost(host)
                    }
                }
            }
        ))
    }
}
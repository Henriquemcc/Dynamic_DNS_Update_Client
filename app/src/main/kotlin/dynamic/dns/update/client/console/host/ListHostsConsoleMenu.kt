package dynamic.dns.update.client.console.host

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.controller.HostsController

/**
 * Console menu which allows the user to list the hosts.
 */
fun listHostsConsoleMenu() {

    StaticConsoleMenu("List hosts menu", listOf(
        ConsoleButton("Print hosts sorted by index") {
            var index = 0
            for (host in HostsController) {
                println("${index++} - $host")
            }
        },
        ConsoleButton("Print hosts sorted by hostname") {
            val hosts = HostsController.sortedBy { it.hostname }
            for (host in hosts) {
                println(host)
            }
        },
        ConsoleButton("Print a host, selecting it by it's index") {
            if (HostsController.isNotEmpty()) {
                val selectedHost = readInteger("Index: ", HostsController.indices)
                println(HostsController[selectedHost])
            }
        },
        ConsoleButton("Print a host, selecting it by it's hostname") {
            if (HostsController.isNotEmpty()) {
                val hostname = readString("Hostname: ")
                println(HostsController.find {
                    it.hostname.lowercase() == hostname.lowercase()
                })
            }
        }
    ))
}
package dynamic.dns.update.client.console.networkInterface

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.console.getConfirmationConsole
import dynamic.dns.update.client.controller.HostsController

/**
 * Console menu which allows the user to remove an allowed network interface name.
 * @param allowedNetworkInterfaces List of allowed network interfaces' name.
 * @return List of allowed network interfaces' name.
 */
internal fun removeNetworkInterface(allowedNetworkInterfaces: MutableList<String> = mutableListOf()): List<String> {

    StaticConsoleMenu("Remove allowed network interface console menu", listOf(
        ConsoleButton("List allowed network interfaces") {
            listNetworkInterfaces(allowedNetworkInterfaces)
        },
        ConsoleButton("Remove allowed network interface by it's index") {
            if (allowedNetworkInterfaces.isNotEmpty()) {
                val index = readInteger("Index: ", allowedNetworkInterfaces.indices)

                if (getConfirmationConsole("Would you like to remove ${allowedNetworkInterfaces[index]} ?")) {
                    allowedNetworkInterfaces.removeAt(index)
                }
            }
        },
        ConsoleButton("Remove network interface by it's name") {
            if (HostsController.isNotEmpty()) {
                val name = readString("name: ")
                val networkInterface = allowedNetworkInterfaces.find {
                    it.lowercase() == name.lowercase()
                }
                if (networkInterface != null) {
                    if (getConfirmationConsole("Would you like to remove $networkInterface ?")) {
                        allowedNetworkInterfaces.remove(networkInterface)
                    }
                }
            }
        }
    ))

    return allowedNetworkInterfaces
}
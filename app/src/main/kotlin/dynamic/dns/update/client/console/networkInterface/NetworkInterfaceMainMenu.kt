package dynamic.dns.update.client.console.networkInterface

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu

/**
 * Console menu which allows the user to manage allowed network interfaces.
 * @param allowedNetworkInterfaces List of allowed network interfaces' name.
 * @return List of allowed network interfaces' name.
 */

fun networkInterfaceMainMenu(allowedNetworkInterfaces: MutableList<String> = mutableListOf()): List<String> {

    StaticConsoleMenu("Network interface menu", listOf(
        ConsoleButton("Show allowed network interfaces") { listNetworkInterfaces(allowedNetworkInterfaces) },
        ConsoleButton("Add allowed network interface") { addNetworkInterface(allowedNetworkInterfaces) },
        ConsoleButton("Remove allowed network interface") { removeNetworkInterface(allowedNetworkInterfaces) }
    )
    )

    return allowedNetworkInterfaces
}

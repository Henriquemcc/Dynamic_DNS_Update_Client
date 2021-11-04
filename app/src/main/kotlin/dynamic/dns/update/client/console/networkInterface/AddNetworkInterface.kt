package dynamic.dns.update.client.console.networkInterface

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import java.net.NetworkInterface

/**
 * Console menu which allows the user to add a network interface to the list of allowed network interfaces' name.
 * @param allowedNetworkInterfaces List of allowed network interfaces' name.
 * @return List of allowed network interfaces' name.
 */
internal fun addNetworkInterface(allowedNetworkInterfaces: MutableList<String> = mutableListOf()): List<String> {

    val networkInterfacesButtons = mutableListOf<ConsoleButton>()
    for (networkInterface in NetworkInterface.getNetworkInterfaces()) {
        networkInterfacesButtons.add(ConsoleButton(networkInterface.name) {
            if (!allowedNetworkInterfaces.contains(networkInterface.name)) {
                allowedNetworkInterfaces.add(networkInterface.name)
            }
        })
    }

    StaticConsoleMenu("Add network interface", networkInterfacesButtons)

    return allowedNetworkInterfaces
}
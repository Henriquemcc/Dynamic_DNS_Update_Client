package dynamic.dns.update.client.console.networkInterface

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.StaticConsoleMenu
import dynamic.dns.update.client.console.common.myio.readInteger
import dynamic.dns.update.client.console.common.myio.readString

/**
 * Console menu which allows the user to list allowed network interfaces' name.
 * @param allowedNetworkInterfaces List of allowed network interfaces' name.
 */
internal fun listNetworkInterfaces(allowedNetworkInterfaces: MutableList<String> = mutableListOf()) {

    StaticConsoleMenu("List allowed network interfaces menu", listOf(
        ConsoleButton("Print allowed network interfaces sorted by index") {
            var index = 0
            for (allowedNetworkInterface in allowedNetworkInterfaces) {
                println("${index++} - $allowedNetworkInterface")
            }

        },
        ConsoleButton("Print allowed network interfaces sorted by name") {
            val networkInterfaces = allowedNetworkInterfaces.sorted()
            for (networkInterface in networkInterfaces) {
                println(networkInterface)
            }
        },
        ConsoleButton("Print allowed network interface, selecting it by it's index") {
            if (allowedNetworkInterfaces.isNotEmpty()) {
                val selectedNetworkInterface = readInteger("Index: ", allowedNetworkInterfaces.indices)
                println(selectedNetworkInterface)
            }
        },
        ConsoleButton("Print allowed network interface, selecting it by it's name") {
            if (allowedNetworkInterfaces.isNotEmpty()) {
                val name = readString("Name: ")
                println(allowedNetworkInterfaces.find {
                    it.lowercase() == name.lowercase()
                })
            }
        }
    ))
}
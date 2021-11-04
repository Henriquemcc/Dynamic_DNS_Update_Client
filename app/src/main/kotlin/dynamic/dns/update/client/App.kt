package dynamic.dns.update.client

import dynamic.dns.update.client.arguments.processCommandLineArguments
import dynamic.dns.update.client.console.MainConsoleMenu
import dynamic.dns.update.client.graphic.MainGraphicMenu
import java.awt.GraphicsEnvironment

/**
 * Main function.
 * @param args Command line arguments.
 */
fun main(args: Array<String>) {

    if (args.isNotEmpty()) {
        processCommandLineArguments(args.toList())
    } else if (GraphicsEnvironment.isHeadless()) {
        MainConsoleMenu()
    } else {
        MainGraphicMenu()
    }

}

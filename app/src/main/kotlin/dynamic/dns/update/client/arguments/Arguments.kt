package dynamic.dns.update.client.arguments

import dynamic.dns.update.client.console.MainConsoleMenu
import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.MainGraphicMenu

/**
 * Process command line arguments.
 * @param args Arguments which will be processed.
 */
fun processCommandLineArguments(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            // Help
            "--help" -> help()
            "-h" -> help()

            // Version
            "--version" -> version()

            // Console
            "console" -> MainConsoleMenu()

            // Graphic User Interface
            "graphic-user-interface" -> MainGraphicMenu()
            "gui" -> MainGraphicMenu()

            // Perform update
            "perform-ip-update" -> performIpUpdate(args - args[0])

            // Perform cleaning
            "perform-ip-cleaning" -> HostsController.performIpCleaning()

            // Host
            "host" -> host(args - args[0])
        }
    }
}


package dynamic.dns.update.client

import dynamic.dns.update.client.arguments.arguments
import dynamic.dns.update.client.console.MainConsoleMenu
import dynamic.dns.update.client.graphic.MainGraphicMenu
import java.awt.GraphicsEnvironment

fun main(args: Array<String> ) {

    if (args.isNotEmpty()) {
        arguments(args.toList())
    }
    else if (GraphicsEnvironment.isHeadless()) {
        MainConsoleMenu()
    }
    else
    {
        MainGraphicMenu()
    }

}

package dynamic.dns.update.client.arguments

import java.io.File

/**
 * Print the main help menu.
 */
internal fun help() {
    val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()
    stringBuilder.appendLine("Usage: java -jar $jarFileName COMMAND [OPTIONS]")
    stringBuilder.appendLine()
    stringBuilder.appendLine("Options:")

    var str = "\t-h, --help"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("display this help and exit")

    str = "\t--version"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("output version information and exit")


    stringBuilder.appendLine("Commands:")

    str = "\tconsole"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Starts console command line interface")

    str = "\tgui, graphic-user-interface"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Starts graphical user interface")

    str = "\tperform-ip-cleaning"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Performs host ip cleaning")

    str = "\tperform-ip-update"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Performs host ip update")

    str = "\thost"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("List, add, edit, remove a Host")

    stringBuilder.appendLine()
    stringBuilder.appendLine("Run 'java -jar $jarFileName COMMAND --help' for more information on a command.")

    println(stringBuilder.toString())
}
package dynamic.dns.update.client.arguments

import dynamic.dns.update.client.controller.HostsController
import java.io.File

internal fun performIpUpdate(args: List<String>) {

    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "--infinite-looping" -> {HostsController.performIpUpdate(true)}
            "-i" -> {HostsController.performIpUpdate(true)}
            "-h" -> performIpUpdateHelp()
            "--help" -> performIpUpdateHelp()
        }
    }
    else
    {
        HostsController.performIpUpdate(false)
    }


}

private fun performIpUpdateHelp()
{
    val jarFileName = File(object{}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()

    stringBuilder.appendLine("Usage: java -jar $jarFileName perform-ip-update [OPTIONS]")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Perform host IP address update")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Options:")

    val str = "\t-i, --infinite-looping"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("run in infinite looping")

    println(stringBuilder.toString())
}
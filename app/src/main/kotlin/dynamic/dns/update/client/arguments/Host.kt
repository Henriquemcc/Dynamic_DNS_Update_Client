package dynamic.dns.update.client.arguments

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.io.File
import java.time.Duration

internal fun host(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "list" -> listHost(args - args[0])
            "add" -> addHost(args - args[0])
            "remove" -> removeHost(args - args[0])
            "edit" -> editHost(args - args[0])
            "-h" -> hostHelp()
            "--help" -> hostHelp()
        }
    }
}

private fun hostHelp() {
    val jarFileName = File(object{}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()
    stringBuilder.appendLine("Usage: java -jar $jarFileName host COMMAND")
    stringBuilder.appendLine()
    stringBuilder.appendLine("Manage hosts")
    stringBuilder.appendLine()
    stringBuilder.appendLine("Commands:")

    var str = "\tlist"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("List hosts")

    str = "\tadd"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Add a host")

    str = "\tremove"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Remove a host")

    str = "\tedit"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Edit a host")

    stringBuilder.appendLine()
    stringBuilder.appendLine("Run 'java -jar $jarFileName host COMMAND --help' for more information on a command.")
    println(stringBuilder.toString())
}

private fun listHost(args: List<String>) {
    if (args.isEmpty() || args[0].lowercase() == "--sort-by-index")
    {
        var index = 0
        for (host in HostsController) {
            println("${index++} - $host")
        }
    }
    else if(args[0].lowercase() == "--sort-by-hostname") {
        val hosts = HostsController.sortedBy { it.hostname }
        for (host in hosts) {
            println(host)
        }
    }
}

private fun addHost(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "DuckDNS" -> addDuckDnsHost(args - args[0])
        }
    }
}

private fun removeHost(args: List<String>) {
    if (args.isNotEmpty())
    {
        when(args[0].lowercase())
        {
            "--hostname" -> removeHostByHostname(args - args[0])
            "--index" -> removeHostByIndex(args - args[0])
        }
    }
}

private fun removeHostByIndex(args: List<String>)
{
    if (args.isNotEmpty())
    {
        HostsController.removeAt(args[0].toInt())
    }
}

private fun removeHostByHostname(args: List<String>)
{
    if (args.isNotEmpty())
    {
        val hostname = args[0]
        HostsController.removeIf {
            it.hostname.lowercase() == hostname.lowercase()
        }
    }
}

private enum class TagType {
    Hostname,
    Token,
    DelayDuration,
    Days,
    Hours,
    Minutes,
    Seconds,
    DisableIPv4,
    DisableIPv6
}

private fun addDuckDnsHost(args: List<String>) {
    if (args.isNotEmpty())
    {
        val tags = mutableMapOf<TagType, Int>()
        tags[TagType.Hostname] = args.map { it.lowercase() }.indexOf("--hostname")
        tags[TagType.Token] = args.map { it.lowercase() }.indexOf("--token")
        tags[TagType.DelayDuration] = args.map { it.lowercase() }.indexOf("--delay-duration")
        tags[TagType.Days] = args.map { it.lowercase() }.indexOf("--days")
        tags[TagType.Hours] = args.map { it.lowercase() }.indexOf("--hours")
        tags[TagType.Minutes] = args.map { it.lowercase() }.indexOf("--minutes")
        tags[TagType.Seconds] = args.map { it.lowercase() }.indexOf("--seconds")
        tags[TagType.DisableIPv4] = args.map { it.lowercase() }.indexOf("--disable-ipv4")
        tags[TagType.DisableIPv6] = args.map { it.lowercase() }.indexOf("--disable-ipv6")

        var hostname: String? = null
        if ((tags[TagType.Hostname] != null) && (tags[TagType.Hostname]!! >= 0) && (!tags.values.contains(tags[TagType.Hostname]!! + 1))) {
            hostname = args[tags[TagType.Hostname]!! + 1]
        }

        var token: String? = null
        if ((tags[TagType.Token] != null) && (tags[TagType.Token]!! >= 0) && (!tags.values.contains(tags[TagType.Token]!! + 1))) {
            token = args[tags[TagType.Token]!! + 1]
        }

        val enableIPv6 = (tags[TagType.DisableIPv6] == null || tags[TagType.DisableIPv6]!! < 0)

        val enableIPv4 = (tags[TagType.DisableIPv4] == null || tags[TagType.DisableIPv4]!! < 0)

        var delayDuration = Duration.ZERO
        if ((tags[TagType.DelayDuration] != null) && (tags[TagType.DelayDuration]!! >= 0)) {

            if ((tags[TagType.Days] != null) && (tags[TagType.Days]!! >= 0) && (!tags.values.contains(tags[TagType.Days]!! + 1))) {
                delayDuration += Duration.ofDays(args[tags[TagType.Days]!! + 1].toLong())
            }

            if ((tags[TagType.Hours] != null) && (tags[TagType.Hours]!! >= 0) && (!tags.values.contains(tags[TagType.Hours]!! + 1))) {
                delayDuration += Duration.ofDays(args[tags[TagType.Hours]!! + 1].toLong())
            }

            if ((tags[TagType.Minutes] != null) && (tags[TagType.Minutes]!! >= 0) && (!tags.values.contains(tags[TagType.Minutes]!! + 1))) {
                delayDuration += Duration.ofDays(args[tags[TagType.Minutes]!! + 1].toLong())
            }

            if ((tags[TagType.Seconds] != null) && (tags[TagType.Seconds]!! >= 0) && (!tags.values.contains(tags[TagType.Seconds]!! + 1))) {
                delayDuration += Duration.ofDays(args[tags[TagType.Seconds]!! + 1].toLong())
            }

        }

        HostsController.add(DuckDnsSubdomain(hostname!!, enableIPv4, enableIPv6, delayDuration, token!!))
    }
}

private fun editHost(args: List<String>) {

}
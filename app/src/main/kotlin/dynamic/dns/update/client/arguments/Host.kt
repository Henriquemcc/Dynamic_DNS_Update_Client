package dynamic.dns.update.client.arguments

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.io.File
import java.time.Duration

/**
 * Process host command line arguments.
 * @param args Command line arguments.
 */
internal fun host(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "list" -> listHost(args - args[0])
            "add" -> addHost(args - args[0])
            "remove" -> removeHost(args - args[0])
            "-h" -> hostHelp()
            "--help" -> hostHelp()
        }
    }
}

/**
 * Print the 'host --help' menu.
 */
private fun hostHelp() {
    val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name
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

    stringBuilder.appendLine()
    stringBuilder.appendLine("Run 'java -jar $jarFileName host COMMAND --help' for more information on a command.")
    println(stringBuilder.toString())
}

/**
 * Process 'host list' command line.
 * @param args Command line arguments.
 */
private fun listHost(args: List<String>) {
    if (args.isEmpty() || args[0].lowercase() == "--sort-by-index") {
        var index = 0
        for (host in HostsController) {
            println("${index++} - $host")
        }
    } else if (args[0].lowercase() == "--sort-by-hostname") {
        val hosts = HostsController.sortedBy { it.hostname }
        for (host in hosts) {
            println(host)
        }
    }
}

/**
 * Process 'host add' command line arguments.
 * @param args Command line arguments.
 */
private fun addHost(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "--duckdns" -> addDuckDnsHost(args - args[0])
            "-h" -> addHostHelp()
            "--help" -> addHostHelp()
        }
    }
}

/**
 * Print the 'host add' help menu.
 */
private fun addHostHelp() {
    val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()

    stringBuilder.appendLine("Usage: java -jar $jarFileName host add [OPTIONS]")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Options:")

    var str = "\t-h, --help"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("display this help and exit")

    str = "\t--duckDNS"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("add a Duck DNS host")

    println(stringBuilder.toString())
}

/**
 * Process the 'host remove' command line arguments.
 * @param args Command line arguments.
 */
private fun removeHost(args: List<String>) {
    if (args.isNotEmpty()) {
        when (args[0].lowercase()) {
            "--hostname" -> removeHostByHostname(args - args[0])
            "--index" -> removeHostByIndex(args - args[0])
            "-h" -> removeHostHelp()
            "--help" -> removeHostHelp()
        }
    }
}

/**
 * Print the 'host remove --help' menu.
 */
private fun removeHostHelp() {

    val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()

    stringBuilder.appendLine("Usage: java -jar $jarFileName host remove [OPTIONS]")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Options:")

    var str = "\t-h, --help"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("display this help and exit")

    str = "\t--index"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("remove a host by it's index")

    str = "\t--hostname"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("remove a host by it's hostname")

    println(stringBuilder.toString())

}

/**
 * Process the 'host remove' command line arguments.
 * @param args Command line arguments.
 */
private fun removeHostByIndex(args: List<String>) {
    if (args.isNotEmpty()) {
        HostsController.removeAt(args[0].toInt())
    }
}

/**
 * Process the 'host remove --hostname' command line arguments.
 * @param args Command line arguments.
 */
private fun removeHostByHostname(args: List<String>) {
    if (args.isNotEmpty()) {
        val hostname = args[0]
        HostsController.removeIf {
            it.hostname.lowercase() == hostname.lowercase()
        }
    }
}

/**
 * Duck DNS enum type tags.
 */
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

/**
 * Process the 'host add --duckDNS' command line arguments.
 * @param args Command line arguments.
 */
private fun addDuckDnsHost(args: List<String>) {
    if (args.isNotEmpty()) {
        if (args[0].lowercase() == "-h" || args[0].lowercase() == "--help") {
            addDuckDnsHostHelp()
        } else {

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
            if ((tags[TagType.Hostname] != null) && ((tags[TagType.Hostname] ?: return) >= 0) && (!tags.values.contains(
                    (tags[TagType.Hostname] ?: return) + 1
                ))
            ) {
                hostname = args[(tags[TagType.Hostname] ?: return) + 1]
            }

            var token: String? = null
            if ((tags[TagType.Token] != null) && ((tags[TagType.Token] ?: return) >= 0) && (!tags.values.contains(
                    (tags[TagType.Token]
                        ?: return) + 1
                ))
            ) {
                token = args[(tags[TagType.Token] ?: return) + 1]
            }

            val enableIPv6 = (tags[TagType.DisableIPv6] == null || (tags[TagType.DisableIPv6] ?: return) < 0)

            val enableIPv4 = (tags[TagType.DisableIPv4] == null || (tags[TagType.DisableIPv4] ?: return) < 0)

            var delayDuration = Duration.ZERO
            if ((tags[TagType.DelayDuration] != null) && ((tags[TagType.DelayDuration] ?: return) >= 0)) {

                if ((tags[TagType.Days] != null) && ((tags[TagType.Days]
                        ?: return) >= 0) && (!tags.values.contains((tags[TagType.Days] ?: return) + 1))
                ) {
                    delayDuration += Duration.ofDays(args[(tags[TagType.Days] ?: return) + 1].toLong())
                }

                if ((tags[TagType.Hours] != null) && ((tags[TagType.Hours]
                        ?: return) >= 0) && (!tags.values.contains((tags[TagType.Hours] ?: return) + 1))
                ) {
                    delayDuration += Duration.ofDays(args[(tags[TagType.Hours] ?: return) + 1].toLong())
                }

                if ((tags[TagType.Minutes] != null) && ((tags[TagType.Minutes]
                        ?: return) >= 0) && (!tags.values.contains(
                        (tags[TagType.Minutes] ?: return) + 1
                    ))
                ) {
                    delayDuration += Duration.ofDays(args[(tags[TagType.Minutes] ?: return) + 1].toLong())
                }

                if ((tags[TagType.Seconds] != null) && ((tags[TagType.Seconds]
                        ?: return) >= 0) && (!tags.values.contains(
                        (tags[TagType.Seconds] ?: return) + 1
                    ))
                ) {
                    delayDuration += Duration.ofDays(args[(tags[TagType.Seconds] ?: return) + 1].toLong())
                }

            }

            HostsController.add(
                DuckDnsSubdomain(
                    hostname ?: return, enableIPv4, enableIPv6, delayDuration,
                    token ?: return
                )
            )
        }
    }
}

/**
 * Print the 'host add --duckDNS --help' menu.
 */
private fun addDuckDnsHostHelp() {
    val jarFileName = File(object {}.javaClass.protectionDomain.codeSource.location.path).name
    val stringBuilder = StringBuilder()

    stringBuilder.appendLine("Usage: java -jar $jarFileName host add --duckDNS [OPTIONS]")

    stringBuilder.appendLine()

    stringBuilder.appendLine("add a Duck DNS host")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Options:")

    var str = "\t--hostname"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Duck DNS hostname")

    str = "\t--token"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Duck DNS token")

    str = "\t--delay-duration"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Duck DNS delay duration")

    stringBuilder.appendLine()

    stringBuilder.appendLine("Delay duration options:")

    stringBuilder.appendLine()

    str = "\t--days"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Number of days")

    str = "\t--hours"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Number of hours")

    str = "\t--minutes"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Number of minutes")

    str = "\t--seconds"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Number of seconds")

    str = "\t--disable-ipv4"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Disable IPv4")

    str = "\t--disable-ipv6"
    stringBuilder.append(str)
    stringBuilder.append(printTab(str.length))
    stringBuilder.appendLine("Disable IPv6")

    println(stringBuilder.toString())
}
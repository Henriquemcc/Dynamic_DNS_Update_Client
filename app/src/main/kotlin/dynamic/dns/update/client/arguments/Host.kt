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
    DisableIPv6,
    NetworkInterfaces
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

            val argsLowerCase = args.map { it.lowercase() }

            val tagsIndex = mutableMapOf<TagType, Int>()
            tagsIndex[TagType.Hostname] = argsLowerCase.indexOf("--hostname")
            tagsIndex[TagType.Token] = argsLowerCase.indexOf("--token")
            tagsIndex[TagType.DelayDuration] = argsLowerCase.indexOf("--delay-duration")
            tagsIndex[TagType.Days] = argsLowerCase.indexOf("--days")
            tagsIndex[TagType.Hours] = argsLowerCase.indexOf("--hours")
            tagsIndex[TagType.Minutes] = argsLowerCase.indexOf("--minutes")
            tagsIndex[TagType.Seconds] = argsLowerCase.indexOf("--seconds")
            tagsIndex[TagType.DisableIPv4] = argsLowerCase.indexOf("--disable-ipv4")
            tagsIndex[TagType.DisableIPv6] = argsLowerCase.indexOf("--disable-ipv6")
            tagsIndex[TagType.NetworkInterfaces] = argsLowerCase.indexOf("--network-interfaces")
            if (tagsIndex.getOrDefault(TagType.NetworkInterfaces, -1) < 0) {
                tagsIndex[TagType.NetworkInterfaces] = argsLowerCase.indexOf("--network-interface")
            }

            // Getting Hostname
            var tagIndex = tagsIndex.getOrDefault(TagType.Hostname, -1)
            if (tagIndex < 0 || (tagIndex + 1) >= args.size || tagsIndex.values.contains(tagIndex + 1))
                return
            val hostname = argsLowerCase[tagIndex + 1]

            // Getting Token
            tagIndex = tagsIndex.getOrDefault(TagType.Token, -1)
            if (tagIndex < 0 || (tagIndex + 1) >= args.size || tagsIndex.values.contains(tagIndex + 1))
                return
            val token = argsLowerCase[tagIndex + 1]

            // Getting enable IPv6
            tagIndex = tagsIndex.getOrDefault(TagType.DisableIPv6, -1)
            val enableIPv6 = (tagIndex < 0)

            // Getting enable IPv4
            tagIndex = tagsIndex.getOrDefault(TagType.DisableIPv4, -1)
            val enableIPv4 = (tagIndex < 0)

            // Getting network interfaces
            tagIndex = tagsIndex.getOrDefault(TagType.NetworkInterfaces, -1)
            val networkInterfaceName = mutableListOf<String>()
            if (tagIndex >= 0 && (tagIndex + 1) < args.size && !tagsIndex.values.contains(tagIndex + 1)) {
                println("oi0")
                if (args[tagIndex + 1].contains("[")) {
                    println("oi1")
                    var index = tagIndex + 1

                    while (index < args.size && !tagsIndex.values.contains(index)) {
                        println("oi2")

                        val line = args[index].replace("[", "").replace(",", "").replace("]", "")
                        if (line.isNotEmpty() && line.isNotBlank()) {
                            println("oi3")
                            networkInterfaceName.add(line)
                        }

                        if (args[index].contains("]")) {
                            break
                        }

                        index++
                    }
                } else {
                    networkInterfaceName.add(args[tagIndex + 1])
                }
            }

            // Getting delay duration
            var delayDuration = Duration.ZERO
            tagIndex = tagsIndex.getOrDefault(TagType.DelayDuration, -1)
            if (tagIndex >= 0) {

                // Getting days
                tagIndex = tagsIndex.getOrDefault(TagType.Days, -1)
                if (tagIndex >= 0 && (tagIndex + 1) < args.size && !tagsIndex.values.contains(tagIndex + 1)) {
                    delayDuration += Duration.ofDays(argsLowerCase[tagIndex + 1].toLong())
                }

                // Getting hours
                tagIndex = tagsIndex.getOrDefault(TagType.Hours, -1)
                if (tagIndex >= 0 && (tagIndex + 1) < args.size && !tagsIndex.values.contains(tagIndex + 1)) {
                    delayDuration += Duration.ofHours(args[tagIndex + 1].toLong())
                }

                // Getting minutes
                tagIndex = tagsIndex.getOrDefault(TagType.Minutes, -1)
                if (tagIndex >= 0 && (tagIndex + 1) < args.size && !tagsIndex.values.contains(tagIndex + 1)) {
                    delayDuration += Duration.ofMinutes(args[tagIndex + 1].toLong())
                }

                // Getting seconds
                tagIndex = tagsIndex.getOrDefault(TagType.Seconds, -1)
                if (tagIndex >= 0 && (tagIndex + 1) < args.size && !tagsIndex.values.contains(tagIndex + 1)) {
                    delayDuration += Duration.ofSeconds(args[tagIndex + 1].toLong())
                }

            }

            HostsController.add(
                DuckDnsSubdomain(
                    hostname,
                    enableIPv4,
                    enableIPv6,
                    delayDuration,
                    token,
                    networkInterfaceName
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
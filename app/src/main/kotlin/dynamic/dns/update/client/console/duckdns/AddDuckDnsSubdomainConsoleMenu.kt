package dynamic.dns.update.client.console.duckdns

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleText
import dynamic.dns.update.client.console.common.menu.DynamicConsoleMenu
import dynamic.dns.update.client.console.common.myio.readLong
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.time.Duration

class AddDuckDnsSubdomainConsoleMenu {

    private var hostname: String = ""
    private var enableIPv4: Boolean = true
    private var enableIPv6: Boolean = true
    private var updateDelayTimeDays: Long = 0
    private var updateDelayTimeHours: Long = 0
    private var updateDelayTimeMinutes: Long = 0
    private var updateDelayTimeSeconds: Long = 0
    private var token: String = ""

    init {
        DynamicConsoleMenu("Add Duck DNS subdomain menu") {
            return@DynamicConsoleMenu listOf(
                ConsoleButton("Save") {
                    val duration = Duration.ofDays(updateDelayTimeDays) +
                            Duration.ofHours(updateDelayTimeHours) +
                            Duration.ofMinutes(updateDelayTimeMinutes) +
                            Duration.ofSeconds(updateDelayTimeSeconds)
                    val host = DuckDnsSubdomain(hostname, enableIPv4, enableIPv6, duration, token)
                    HostsController.add(host)
                },
                ConsoleButton("Hostname = $hostname") {
                    hostname = readString("Hostname: ")
                },
                ConsoleButton("Token = $token") {
                    token = readString("Token: ")
                },
                ConsoleButton("Enable IPv4 = $enableIPv4") {
                    enableIPv4 = (!enableIPv4)
                },
                ConsoleButton("Enable IPv6 = $enableIPv6") {
                    enableIPv6 = (!enableIPv6)
                },
                ConsoleText("Delay duration time:"),
                ConsoleButton("Days = $updateDelayTimeDays") {
                    updateDelayTimeDays = readLong("Delay time (days): ")
                },
                ConsoleButton("Hours = $updateDelayTimeHours") {
                    updateDelayTimeHours = readLong("Delay time (hours): ")
                },
                ConsoleButton("Minutes = $updateDelayTimeMinutes") {
                    updateDelayTimeMinutes = readLong("Delay time (minutes): ")
                },
                ConsoleButton("Seconds = $updateDelayTimeSeconds") {
                    updateDelayTimeSeconds = readLong("Delay time (seconds): ")
                }
            )
        }
    }
}

package dynamic.dns.update.client.console.host.duckdns

import dynamic.dns.update.client.console.common.menu.ConsoleButton
import dynamic.dns.update.client.console.common.menu.ConsoleText
import dynamic.dns.update.client.console.common.menu.DynamicConsoleMenu
import dynamic.dns.update.client.console.common.myio.readLong
import dynamic.dns.update.client.console.common.myio.readString
import dynamic.dns.update.client.console.getConfirmationConsole
import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.time.Duration

/**
 * Console menu which allows the user to modify a Duck DNS subdomain host.
 * @param oldHost The duck dns subdomain host which will be modified.
 */
fun updateDuckDnsHost(oldHost: DuckDnsSubdomain) {

    var hostname: String = oldHost.hostname
    var enableIPv4: Boolean = oldHost.enableIPv4
    var enableIPv6: Boolean = oldHost.enableIPv6
    var updateDelayTimeDays: Long = oldHost.updateDelayTime.seconds / 60 / 60 / 24
    var updateDelayTimeHours: Long = (oldHost.updateDelayTime.seconds / 60 / 60) % 24
    var updateDelayTimeMinutes: Long = (oldHost.updateDelayTime.seconds / 60) % 60
    var updateDelayTimeSeconds: Long = oldHost.updateDelayTime.seconds % 60
    var token: String = oldHost.token

    DynamicConsoleMenu("Update Duck DNS subdomain menu") {
        return@DynamicConsoleMenu listOf(
            ConsoleButton("Save") {
                val duration = Duration.ofDays(updateDelayTimeDays) +
                        Duration.ofHours(updateDelayTimeHours) +
                        Duration.ofMinutes(updateDelayTimeMinutes) +
                        Duration.ofSeconds(updateDelayTimeSeconds)
                val newHost = DuckDnsSubdomain(hostname, enableIPv4, enableIPv6, duration, token)
                val confirmationMessage = StringBuilder()
                confirmationMessage.appendLine("Would you like to update :")
                confirmationMessage.appendLine(oldHost)
                confirmationMessage.appendLine("to")
                confirmationMessage.appendLine(newHost)
                confirmationMessage.append("?")
                if (getConfirmationConsole(confirmationMessage.toString())) {
                    HostsController.remove(oldHost)
                    HostsController.add(newHost)
                }
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
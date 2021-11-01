package dynamic.dns.update.client.console

import dynamic.dns.update.client.console.common.myio.readString

fun getConfirmationConsole(command: String = ""): Boolean {

    val message = StringBuilder()
    message.appendLine(command)
    message.appendLine("Y - Yes")
    message.appendLine("N - No")
    message.append("> ")

    var input = ""
    while (input.isEmpty() || (input[0] != 'y' && input[0] != 'n')) {
        input = readString(message.toString()).lowercase()
    }

    return input[0] == 'y'
}
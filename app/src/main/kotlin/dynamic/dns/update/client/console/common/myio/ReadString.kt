package dynamic.dns.update.client.console.common.myio

/**
 * Prints the instruction message and retrieves from the user a string.
 * @param instructionMessage Message which will instruct the user what to type.
 * @return The string typed by the user.
 */
fun readString(instructionMessage: String? = null): String {
    if (instructionMessage != null) {
        print(instructionMessage)
    }

    return readLine()?.trim() ?: ""
}
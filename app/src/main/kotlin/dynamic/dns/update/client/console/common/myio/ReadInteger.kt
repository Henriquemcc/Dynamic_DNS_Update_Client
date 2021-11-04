package dynamic.dns.update.client.console.common.myio

import java.text.MessageFormat

/**
 * Prints the instruction message and retrieves from the user an integer number in the specified range.
 * @param instructionMessage Message which will instruct the user what to type.
 * @param range Range of the input which will be enforced.
 * @return The integer value typed by the user.
 */
fun readInteger(instructionMessage: String? = null, range: IntRange? = null): Int {

    var number: Int? = null
    while (number == null || range?.contains(number) == false) {
        val stringRead = readString(instructionMessage)
        try {
            number = stringRead.toInt()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (range?.contains(number) == false) {
            println(MessageFormat.format("{0} is not in {1}", number, range))
        }
    }

    return number

}
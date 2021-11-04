package dynamic.dns.update.client.console.common.myio

import java.text.MessageFormat

/**
 * Prints the instruction message and retrieves from the user a long integer number in the specified range.
 * @param instructionMessage Message which will instruct the user what to type.
 * @param range Range of the input which will be enforced.
 * @return The long integer value typed by the user.
 */
fun readLong(instructionMessage: String? = null, range: LongRange? = null): Long {

    var number: Long? = null
    while (number == null || range?.contains(number) == false) {
        val stringRead = readString(instructionMessage)
        try {
            number = stringRead.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (range?.contains(number) == false) {
            println(MessageFormat.format("{0} is not in {1}", number, range))
        }
    }

    return number

}
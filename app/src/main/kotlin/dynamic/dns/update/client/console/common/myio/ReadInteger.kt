package dynamic.dns.update.client.console.common.myio

import java.text.MessageFormat

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
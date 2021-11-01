package dynamic.dns.update.client.console.common.myio

import java.text.MessageFormat

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
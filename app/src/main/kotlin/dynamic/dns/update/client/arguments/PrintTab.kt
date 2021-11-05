package dynamic.dns.update.client.arguments

/**
 * Prints tabs according to the length of the string before it.
 * @param lengthStringBeforeIt The length of the string before the tabs.
 */
fun printTab(lengthStringBeforeIt: Int): String {

    var resultingSpace = 81 - lengthStringBeforeIt
    if (resultingSpace < 0)
        resultingSpace = 1

    return " ".repeat(resultingSpace)
}
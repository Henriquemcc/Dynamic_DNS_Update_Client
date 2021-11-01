package dynamic.dns.update.client.console.common.menu

import dynamic.dns.update.client.console.common.myio.printHeader
import dynamic.dns.update.client.console.common.myio.readInteger

class DynamicConsoleMenu(
    title: String? = null,
    private val optionsText: ConsoleText = ConsoleText("Options: "),
    private val exitText: ConsoleText = ConsoleText("Exit"),
    val getOptions: () -> List<ConsoleOption>
) : ConsoleMenu() {

    private fun getButtons(): List<ConsoleButton> = getOptions().filterIsInstance<ConsoleButton>()

    private fun getNumberOfButtons(): Int = getButtons().size

    private fun getMessage(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine(optionsText.text)
        stringBuilder.appendLine("0 - ${exitText.text}")

        var buttonIndex = 1
        for (option in getOptions()) {

            if (option is ConsoleText) {
                stringBuilder.appendLine(option.text)
            } else if (option is ConsoleButton) {
                stringBuilder.appendLine("${buttonIndex++} - ${option.text}")
            }
        }

        stringBuilder.append("> ")

        return stringBuilder.toString()
    }

    private fun getInput(): Int = readInteger(getMessage(), 0..getNumberOfButtons())

    init {

        var selectedButton = -1
        while (selectedButton != 0) {

            if (title != null) {
                printHeader(title)
            }

            selectedButton = getInput()

            if (selectedButton != 0) {
                getButtons()[selectedButton - 1].onPressed()
            }
        }
    }
}


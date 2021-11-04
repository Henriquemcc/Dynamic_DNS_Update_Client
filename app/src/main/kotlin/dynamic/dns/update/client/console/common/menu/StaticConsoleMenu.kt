package dynamic.dns.update.client.console.common.menu

import dynamic.dns.update.client.console.common.myio.printHeader
import dynamic.dns.update.client.console.common.myio.readInteger

/**
 * A console menu which its message will not change during the execution.
 * @param title Menu title.
 * @param options Console menu options.
 * @param optionsText Text which will introduce the options available.
 * @param exitText Text which will display the exit option.
 */
class StaticConsoleMenu(
    title: String? = null,
    private val options: List<ConsoleOption>,
    private val optionsText: ConsoleText = ConsoleText("Options: "),
    private val exitText: ConsoleText = ConsoleText("Exit")
) : ConsoleMenu() {

    /**
     * Retrieves console buttons from console options.
     * @return List of console buttons.
     */
    private fun getButtons(): List<ConsoleButton> = options.filterIsInstance<ConsoleButton>()

    /**
     * Retrieves the number of buttons from the console buttons.
     * @return Number of buttons.
     */
    private fun getNumberOfButtons(): Int = getButtons().size

    /**
     * Creates a message string.
     * @return Message string.
     */
    private fun getMessage(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendLine(optionsText.text)
        stringBuilder.appendLine("0 - ${exitText.text}")

        var buttonIndex = 1
        for (option in options) {

            if (option is ConsoleText) {
                stringBuilder.appendLine(option.text)
            } else if (option is ConsoleButton) {
                stringBuilder.appendLine("${buttonIndex++} - ${option.text}")
            }
        }

        stringBuilder.append("> ")

        return stringBuilder.toString()
    }

    /**
     * Retrieves user input option.
     * @return Option selected by the user.
     */
    private fun getInput(): Int = readInteger(getMessage(), 0..getNumberOfButtons())

    /**
     * Initializer block
     */
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
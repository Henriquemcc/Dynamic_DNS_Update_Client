package dynamic.dns.update.client.console.common.menu

/**
 * Console button, which contains its text and the action to do when it is selected.
 * @param text Button's text explaining what it does.
 * @param onPressed Action to do when the button is pressed by the user.
 */
class ConsoleButton(val text: String, val onPressed: () -> Unit) : ConsoleOption()
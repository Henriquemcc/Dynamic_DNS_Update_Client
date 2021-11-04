package dynamic.dns.update.client.console.common.menu

/**
 * Console button, which contains its text and the action to do when it is selected.
 */
class ConsoleButton(val text: String, val onPressed: () -> Unit) : ConsoleOption()
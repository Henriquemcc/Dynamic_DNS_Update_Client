package dynamic.dns.update.client.graphic

import javax.swing.JFrame
import javax.swing.JOptionPane

/**
 * Retrieves from the user whether the confirmation.
 * @param message Message which will instruct the user what to confirm.
 * @return The user confirmation.
 */
fun getConfirmationGraphic(message: String = ""): Boolean {
    val jFrame = JFrame()
    jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    jFrame.font = getDefaultFont()
    jFrame.isVisible = true
    jFrame.pack()
    val confirmation = JOptionPane.showConfirmDialog(jFrame, message)
    jFrame.dispose()
    return confirmation == JOptionPane.YES_OPTION
}
package dynamic.dns.update.client.graphic

import javax.swing.JFrame
import javax.swing.JOptionPane

fun getConfirmationGraphic(message: String = ""): Boolean {
    val jFrame = JFrame()
    jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    jFrame.font = getDefaultFont()
    jFrame.isVisible = true
    val confirmation = JOptionPane.showConfirmDialog(jFrame, message)
    jFrame.pack()
    return confirmation == JOptionPane.YES_OPTION
}
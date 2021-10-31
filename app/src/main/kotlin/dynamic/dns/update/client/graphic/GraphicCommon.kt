package dynamic.dns.update.client.graphic

import java.awt.Font
import java.awt.Point
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.JOptionPane

val defaultFont: Font = Font("Arial", Font.PLAIN, 20)

fun getConfirmation(message: String = ""): Boolean {
    val jFrame = JFrame()
    jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
    jFrame.font = defaultFont
    jFrame.isVisible = true
    val confirmation = JOptionPane.showConfirmDialog(jFrame, message)
    jFrame.pack()
    return confirmation == JOptionPane.YES_OPTION
}

val JFrame.defaultLocation: Point
    get() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        return Point(screenSize.width / 2 - this.size.width / 2, screenSize.height / 2 - this.size.height / 2)
    }
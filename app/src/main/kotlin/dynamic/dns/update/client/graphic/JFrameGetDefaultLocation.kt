package dynamic.dns.update.client.graphic

import java.awt.Point
import java.awt.Toolkit
import javax.swing.JFrame


fun JFrame.getDefaultLocation(): Point {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    return Point(screenSize.width / 2 - this.size.width / 2, screenSize.height / 2 - this.size.height / 2)
}
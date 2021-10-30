package dynamic.dns.update.client.graphic

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

abstract class Menu (val previousMenu: Menu?) {
    
    protected abstract val jFrame: JFrame
    protected var nextMenu: Menu? = null
    abstract val title: String

    var isVisible: Boolean
        get() = jFrame.isVisible
        set(value) {
            jFrame.isVisible = value
        }

    protected open fun initializeJFrame(): JFrame {
        val jFrame = JFrame(title)
        jFrame.isVisible = true
        jFrame.font = defaultFont
        jFrame.location = jFrame.defaultLocation
        jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        jFrame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    previousMenu?.isVisible = true
                    super.windowClosed(e)
                }
            }
        )
        return jFrame
    }
}
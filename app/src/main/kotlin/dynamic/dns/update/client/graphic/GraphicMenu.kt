package dynamic.dns.update.client.graphic

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

abstract class GraphicMenu(val previousGraphicMenu: GraphicMenu?) {

    protected abstract val jFrame: JFrame
    protected var nextGraphicMenu: GraphicMenu? = null
    abstract val title: String

    var isVisible: Boolean
        get() = jFrame.isVisible
        set(value) {
            jFrame.isVisible = value
        }

    protected open fun initializeJFrame(): JFrame {
        val jFrame = JFrame(title)
        jFrame.isVisible = true
        jFrame.font = getDefaultFont()
        jFrame.location = jFrame.getDefaultLocation()
        jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        jFrame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    previousGraphicMenu?.isVisible = true
                    super.windowClosed(e)
                }
            }
        )
        return jFrame
    }
}
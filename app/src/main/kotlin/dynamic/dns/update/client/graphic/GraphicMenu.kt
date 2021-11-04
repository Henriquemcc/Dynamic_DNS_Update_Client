package dynamic.dns.update.client.graphic

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

/**
 * Graphic menu which will be a model to a graphic user interface menu.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
abstract class GraphicMenu(val previousGraphicMenu: GraphicMenu?) {

    /**
     * JFrame object of this menu.
     */
    protected abstract val jFrame: JFrame

    /**
     * Reference to the next graphical user interface menu.
     */
    protected var nextGraphicMenu: GraphicMenu? = null

    /**
     * Title of this menu.
     */
    abstract val title: String

    /**
     * A control variable which will make the menu JFrame visible / invisible.
     */
    var isVisible: Boolean
        get() = jFrame.isVisible
        set(value) {
            jFrame.isVisible = value
        }

    /**
     * Initializes JFrame.
     * @return JFrame object initialized.
     */
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
package dynamic.dns.update.client.graphic

import dynamic.dns.update.client.controller.HostsController
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Main graphical user interface menu.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
class MainGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {
    override val jFrame: JFrame = initializeJFrame()
    override val title: String = "Main menu"

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    /**
     * Initializes the main JPanel.
     * @return Main JPanel.
     */
    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(4, 1))
        jPanel.add(initializeJButtonHostMenu())
        jPanel.add(initializeJButtonPerformHostIpCleaning())
        jPanel.add(initializeJButtonPerformHostIpUpdate())
        jPanel.add(initializeJButtonPerformHostIpUpdateInInfiniteLooping())
        return jPanel
    }

    /**
     * Initializes the JButton perform host ip cleaning.
     * @return JButton perform host ip cleaning initialized.
     */
    private fun initializeJButtonPerformHostIpCleaning(): JButton {
        val jButton = JButton("Perform host IP cleaning")
        jButton.addActionListener {
            isVisible = false
            val thread = object : Thread() {
                override fun run() {
                    HostsController.performIpCleaning()
                }
            }
            thread.start()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }

    /**
     * Initializes JButton host menu.
     * @return JButton host menu initialized.
     */
    private fun initializeJButtonHostMenu(): JButton {
        val jButton = JButton("Host menu")
        jButton.addActionListener {
            isVisible = false
            nextGraphicMenu = HostGraphicMenu(this)
        }
        return jButton
    }

    /**
     * Initializes JButton perform host IP update.
     * @return JButton perform host IP update.
     */
    private fun initializeJButtonPerformHostIpUpdate(): JButton {
        val jButton = JButton("Perform host IP update")
        jButton.addActionListener {
            isVisible = false
            val thread = object : Thread() {
                override fun run() {
                    HostsController.performIpUpdate()
                }
            }
            thread.start()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }

    /**
     * Initializes JButton perform host IP update in infinite looping.
     * @return JButton perform host IP update in infinite looping.
     */
    private fun initializeJButtonPerformHostIpUpdateInInfiniteLooping(): JButton {
        val jButton = JButton("Perform host IP update in infinite looping")
        jButton.addActionListener {
            isVisible = false
            val thread = object : Thread() {
                override fun run() {
                    HostsController.performIpUpdate(true)
                }
            }
            thread.start()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }
}
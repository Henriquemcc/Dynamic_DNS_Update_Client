package dynamic.dns.update.client.graphic

import dynamic.dns.update.client.controller.HostsController
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class MainGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {
    override val jFrame: JFrame = initializeJFrame()
    override val title: String = "Main menu"

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(4, 1))
        jPanel.add(initializeJButtonHostMenu())
        jPanel.add(initializeJButtonPerformHostCleaning())
        jPanel.add(initializeJButtonPerformHostUpdate())
        jPanel.add(initializeJButtonPerformHostUpdateInInfiniteLooping())
        return jPanel
    }

    private fun initializeJButtonPerformHostCleaning(): JButton {
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

    private fun initializeJButtonHostMenu(): JButton {
        val jButton = JButton("Host menu")
        jButton.addActionListener {
            isVisible = false
            nextGraphicMenu = HostGraphicMenu(this)
        }
        return jButton
    }

    private fun initializeJButtonPerformHostUpdate(): JButton {
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

    private fun initializeJButtonPerformHostUpdateInInfiniteLooping(): JButton {
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
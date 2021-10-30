package dynamic.dns.update.client.graphic

import dynamic.dns.update.client.controller.HostsController
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class MainMenu(previousMenu: Menu? = null) : Menu(previousMenu)
{
    override val jFrame: JFrame = initializeJFrame()
    override val title: String = "Main menu"

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(3, 1))
        jPanel.add(initializeJButtonHostMenu())
        jPanel.add(initializeJButtonPerformHostUpdate())
        jPanel.add(initializeJButtonPerformHostUpdateInInfiniteLooping())
        return jPanel
    }

    private fun initializeJButtonHostMenu(): JButton {
        val jButton = JButton("Host menu")
        jButton.addActionListener {
            isVisible = false
            nextMenu = HostMenu(this)
        }
        return jButton
    }

    private fun initializeJButtonPerformHostUpdate(): JButton {
        val jButton = JButton("Perform host update")
        jButton.addActionListener {
            isVisible = false
            val thread = object : Thread() {
                override fun run() {
                    HostsController.performUpdate()
                }
            }
            thread.start()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }

    private fun initializeJButtonPerformHostUpdateInInfiniteLooping(): JButton {
        val jButton = JButton("Perform host update in infinite looping")
        jButton.addActionListener {
            isVisible = false
            val thread = object : Thread() {
                override fun run() {
                    HostsController.performUpdate(true)
                }
            }
            thread.start()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }
}
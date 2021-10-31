package dynamic.dns.update.client.graphic

import dynamic.dns.update.client.graphic.duckdns.DuckDnsHostMainGraphicMenu
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class HostGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    override val title: String = "Host menu"
    override val jFrame = initializeJFrame()

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(1, 1))
        jPanel.add(initializeJButtonDuckDnsSubdomain())
        return jPanel
    }

    private fun initializeJButtonDuckDnsSubdomain(): JButton {
        val jButton = JButton("Duck DNS subdomain")
        jButton.addActionListener {
            isVisible = false
            nextGraphicMenu = DuckDnsHostMainGraphicMenu(this)
        }
        return jButton
    }

}
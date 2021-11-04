package dynamic.dns.update.client.graphic

import dynamic.dns.update.client.graphic.duckdns.DuckDnsHostMainGraphicMenu
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Graphic menu which will allow the user to manage hosts.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
class HostGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    override val title: String = "Host menu"
    override val jFrame: JFrame = initializeJFrame()

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    /**
     * Initializes the main JPanel.
     * @return Main JPanel initialized.
     */
    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(1, 1))
        jPanel.add(initializeJButtonDuckDnsSubdomain())
        return jPanel
    }

    /**
     * Initializes the JButton Duck DNS subdomain.
     * @return JButton Duck DNS subdomain.
     */
    private fun initializeJButtonDuckDnsSubdomain(): JButton {
        val jButton = JButton("Duck DNS subdomain")
        jButton.addActionListener {
            isVisible = false
            nextGraphicMenu = DuckDnsHostMainGraphicMenu(this)
        }
        return jButton
    }

}
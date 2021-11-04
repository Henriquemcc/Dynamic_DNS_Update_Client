package dynamic.dns.update.client.graphic.networkInterface

import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.WindowEvent
import java.net.NetworkInterface
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JFrame
import javax.swing.JPanel

class NetworkInterfaceAddMenu(
    previousGraphicMenu: GraphicMenu? = null,
    val allowedNetworkInterfaces: MutableList<String> = mutableListOf()) : GraphicMenu(previousGraphicMenu) {

    override val title: String = "Add network interface"
    private val networkInterfacesNamesAvailableToAdd = initializeNetworkInterfacesNamesAvailableToAdd()
    private val jCheckBoxes = initializesJCheckBoxes()

    override val jFrame: JFrame = initializeJFrame()

    private fun initializeNetworkInterfacesNamesAvailableToAdd(): List<String> {
        val list = NetworkInterface.getNetworkInterfaces().toList().map { it.name } as MutableList<String>
        list.removeAll(allowedNetworkInterfaces)
        return list.toList()
    }

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    /**
     * Initializes main JPanel.
     * @return Main JPanel.
     */
    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(networkInterfacesNamesAvailableToAdd.size + 1, 1))
        for (jCheckBox in jCheckBoxes) {
            jPanel.add(jCheckBox.value)
        }
        jPanel.add(initializeJButtonApply())
        return jPanel
    }

    private fun initializeJButtonApply(): JButton {
        val jButton = JButton("Apply")
        jButton.addActionListener {
            for ((key, value) in jCheckBoxes) {
                if (value.isSelected) {
                    allowedNetworkInterfaces.add(key)
                }
            }
            jFrame.dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
            previousGraphicMenu?.isVisible = true
            (previousGraphicMenu as? NetworkInterfaceMainMenu)?.fireTableDataChanged()
            isVisible = false
        }

        return jButton
    }

    private fun initializesJCheckBoxes(): Map<String, JCheckBox> {
        val jCheckBoxes = mutableMapOf<String, JCheckBox>()

        for (name in networkInterfacesNamesAvailableToAdd) {
            val jCheckBox = JCheckBox(name, false)
            jCheckBox.isEnabled = true
            jCheckBox.font = getDefaultFont()
            jCheckBoxes[name] = jCheckBox
        }

        return jCheckBoxes
    }
}
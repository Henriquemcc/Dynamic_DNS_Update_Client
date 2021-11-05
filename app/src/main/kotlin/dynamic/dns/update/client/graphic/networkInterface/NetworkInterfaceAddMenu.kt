package dynamic.dns.update.client.graphic.networkInterface

import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.GridLayout
import java.awt.event.WindowEvent
import java.net.NetworkInterface
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Graphic menu which will allow the user to add network interfaces.
 * @param allowedNetworkInterfaces List of allowed network interfaces.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
internal class NetworkInterfaceAddMenu(
    previousGraphicMenu: GraphicMenu? = null,
    private val allowedNetworkInterfaces: MutableList<String> = mutableListOf()
) : GraphicMenu(
    previousGraphicMenu
) {

    override val title: String = "Add network interface"

    /**
     * List of network interfaces' name available to add.
     */
    private val networkInterfacesNamesAvailableToAdd = initializeNetworkInterfacesNamesAvailableToAdd()

    /**
     * JCheckboxes with all network interfaces' name available to add.
     */
    private val jCheckBoxes = initializesJCheckBoxes()

    override val jFrame: JFrame = initializeJFrame()

    /**
     * Initializes the names of network interfaces which are available to add.
     * @return List of network interfaces' name available to add.
     */
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
        jPanel.add(initializeJButtonAdd())
        return jPanel
    }

    /**
     * Initializes JButton add.
     * @return JButton add initialized.
     */
    private fun initializeJButtonAdd(): JButton {
        val jButton = JButton("Add")
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

    /**
     * Initializes JCheckBoxes with the network interfaces' available to add.
     * @return Map of JCheckBoxes and Strings in which each String is the name of the interface and each JCheckBox
     * is the checkbox to add the interface name.
     */
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
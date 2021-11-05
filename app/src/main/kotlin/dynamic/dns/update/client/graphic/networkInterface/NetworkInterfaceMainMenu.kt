package dynamic.dns.update.client.graphic.networkInterface

import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getConfirmationGraphic
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.GridLayout
import javax.swing.*

/**
 * Graphic menu which will allow the user to add or remove network interfaces.
 * @param allowedNetworkInterfaces List of allowed network interfaces.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
class NetworkInterfaceMainMenu(
    previousGraphicMenu: GraphicMenu? = null,
    private val allowedNetworkInterfaces: MutableList<String> = mutableListOf()
) : GraphicMenu(previousGraphicMenu) {

    /**
     * Table model for allowed network interfaces.
     */
    private val tableModel = TableModel(allowedNetworkInterfaces)

    /**
     * JTable which will show the network interfaces' name.
     */
    private val jTable: JTable = initializeJTable()

    override val title: String = "Allowed network interface menu"

    override val jFrame: JFrame = initializeJFrame()

    /**
     * Initializes the JTable which will show the network interfaces' name.
     * @return JTable initialized.
     */
    private fun initializeJTable(): JTable {
        val jTable = JTable()
        jTable.font = getDefaultFont()
        jTable.model = this.tableModel
        jTable.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        return jTable
    }

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
        val jPanel = JPanel(GridLayout(1, 2))
        jPanel.add(initializeJPanelButtons())
        jPanel.add(initializeJScrollPaneTable())
        return jPanel
    }

    /**
     * Initializes the JScrollPane table which will have the JTable.
     * @return JScrollPane initialized.
     */
    private fun initializeJScrollPaneTable(): JScrollPane {
        return JScrollPane(jTable)
    }

    /**
     * Initializes the JPanel which will store the buttons to add or remove the network interfaces' name.
     * @return JPanel with JButtons initialized.
     */
    private fun initializeJPanelButtons(): JPanel {
        val jPanel = JPanel(GridLayout(2, 1))
        jPanel.add(initializeJButtonAdd())
        jPanel.add(initializeJButtonRemove())
        return jPanel
    }

    /**
     * Initializes the JButton remove.
     * @return JButton remove initialized.
     */
    private fun initializeJButtonRemove(): JButton {
        val jButton = JButton("Remove")
        jButton.addActionListener {
            val allowedNetworkInterface = allowedNetworkInterfaces[jTable.selectedRow]
            if (getConfirmationGraphic("Would you like to remove $allowedNetworkInterface ?")) {
                allowedNetworkInterfaces.remove(allowedNetworkInterface)
                fireTableDataChanged()
            }
        }
        return jButton
    }

    /**
     * Initializes JButton add.
     * @return JButton add initialized.
     */
    private fun initializeJButtonAdd(): JButton {
        val jButton = JButton("Add")
        jButton.addActionListener {
            isVisible = false
            nextGraphicMenu = NetworkInterfaceAddMenu(this, allowedNetworkInterfaces)
        }
        return jButton
    }

    /**
     * Forces TableModel to update it's data.
     */
    fun fireTableDataChanged() {
        tableModel.fireTableDataChanged()
        jFrame.pack()
    }
}
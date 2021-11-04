package dynamic.dns.update.client.graphic.networkInterface

import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getConfirmationGraphic
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.GridLayout
import javax.swing.*

class NetworkInterfaceMainMenu(
    previousGraphicMenu: GraphicMenu? = null,
    val allowedNetworkInterfaces: MutableList<String> = mutableListOf()
) : GraphicMenu(previousGraphicMenu) {

    private val tableModel = TableModel(allowedNetworkInterfaces)

    private val jTable: JTable = initializeJTable()

    override val title: String = "Allowed network interface menu"

    override val jFrame: JFrame = initializeJFrame()

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

    private fun initializeJScrollPaneTable(): JScrollPane {
        return JScrollPane(jTable)
    }

    private fun initializeJPanelButtons(): JPanel {
        val jPanel = JPanel(GridLayout(2, 1))
        jPanel.add(initializeJButtonAdd())
        jPanel.add(initializeJButtonRemove())
        return jPanel
    }

    private fun initializeJButtonRemove(): JButton {
        val jButton = JButton("Remove")
        jButton.addActionListener {
            val allowedNetworkInterface = allowedNetworkInterfaces[jTable.selectedRow]
            if (getConfirmationGraphic("Would you like to remove $allowedNetworkInterface ?")) {
                allowedNetworkInterfaces.remove(allowedNetworkInterface)
            }
        }
        return jButton
    }

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
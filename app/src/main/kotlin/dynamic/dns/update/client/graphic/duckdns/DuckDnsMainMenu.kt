package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getConfirmationGraphic
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

/**
 *  * Graphic menu which will allow the user to manage Duck DNS hosts.
 *  @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
class DuckDnsMainMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    /**
     * Table model which will show Duck DNS subdomains hosts.
     */
    private val tableModel: TableModel = TableModel()

    /**
     * JTable which will show Duck DNS subdomains hosts.
     */
    private val jTable: JTable = initializeJTable()

    override val title: String = "Duck DNS subdomain menu"
    override val jFrame: JFrame = initializeJFrame()

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
        val jPanel = JPanel(FlowLayout())
        jPanel.add(initializeJPanelButtons())
        jPanel.add(initializeJScrollPaneTable())
        return jPanel
    }

    /**
     * Initializes JPanel which will store the buttons create, retrieve, update, delete.
     * @return JPanel with those buttons.
     */
    private fun initializeJPanelButtons(): JPanel {
        val jPanel = JPanel(GridLayout(4, 1))
        jPanel.add(initializeJButtonAdd())
        jPanel.add(initializeJButtonRefresh())
        jPanel.add(initializeJButtonModify())
        jPanel.add(initializeJButtonRemove())
        return jPanel
    }

    /**
     * Initializes JButton create, which on pressed, creates a Duck DNS host.
     * @return JButton create.
     */
    private fun initializeJButtonAdd(): JButton {
        val jButton = JButton("Add")
        jButton.addActionListener {
            nextGraphicMenu = AddHostMenu(this)
            isVisible = false
        }
        return jButton
    }

    /**
     * Initializes JButton retrieve, which on pressed, retrieves Duck DNS hosts.
     * @return JButton retrieve.
     */
    private fun initializeJButtonRefresh(): JButton {
        val jButton = JButton("Refresh")
        jButton.addActionListener {
            isVisible = false
            fireTableDataChanged()
            Thread.sleep((5000).toLong())
            isVisible = true
        }
        return jButton
    }

    /**
     * Initializes JButton update, which on pressed, updates the selected Duck DNS host.
     * @return JButton update.
     */
    private fun initializeJButtonModify(): JButton {
        val jButton = JButton("Modify")
        jButton.addActionListener {
            val selectedRow = jTable.selectedRow
            val oldDuckDnsSubdomain = duckDnsHosts[selectedRow]
            nextGraphicMenu = ModifyHostMenu(this, oldDuckDnsSubdomain)
            fireTableDataChanged()
            isVisible = false
        }
        return jButton
    }

    /**
     * Initializes JButton delete, which on pressed, deletes the selected Duck DNS host.
     * @return JButton delete.
     */
    private fun initializeJButtonRemove(): JButton {
        val jButton = JButton("Remove")
        jButton.addActionListener {
            val selectedRow = jTable.selectedRow
            val removedDuckDnsSubdomain = duckDnsHosts[selectedRow]
            if (getConfirmationGraphic("Would you like to remove $removedDuckDnsSubdomain?")) {
                HostsController.remove(removedDuckDnsSubdomain)
                fireTableDataChanged()
            }
        }
        return jButton
    }

    /**
     * Initializes JScrollPane which will store JTable graphic component.
     * @return JScrollPane with JTable.
     */
    private fun initializeJScrollPaneTable(): JScrollPane {
        return JScrollPane(jTable)
    }

    /**
     * Initializes JTable, which will show Duck DNS hosts.
     * @return JTable.
     */
    private fun initializeJTable(): JTable {
        val jTable = JTable()
        jTable.font = getDefaultFont()
        jTable.model = this.tableModel
        jTable.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        return jTable
    }

    /**
     * Forces TableModel to update it's data.
     */
    fun fireTableDataChanged() {
        tableModel.fireTableDataChanged()
        jFrame.pack()
    }
}
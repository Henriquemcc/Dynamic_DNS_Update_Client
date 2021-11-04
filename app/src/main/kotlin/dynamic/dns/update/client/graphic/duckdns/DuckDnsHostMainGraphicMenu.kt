package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.getConfirmationGraphic
import dynamic.dns.update.client.graphic.getDefaultFont
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class DuckDnsHostMainGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    /**
     * Table model which will show Duck DNS subdomains hosts.
     */
    private val tableModel: DuckDnsSubdomainTableModel = DuckDnsSubdomainTableModel()

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
        jPanel.add(initializeJButtonCreate())
        jPanel.add(initializeJButtonRetrieve())
        jPanel.add(initializeJButtonUpdate())
        jPanel.add(initializeJButtonDelete())
        return jPanel
    }

    /**
     * Initializes JButton create, which on pressed, creates a Duck DNS host.
     * @return JButton create.
     */
    private fun initializeJButtonCreate(): JButton {
        val jButton = JButton("Create")
        jButton.addActionListener {
            DuckDnsCreateHostGraphicMenu(this)
            isVisible = false
        }
        return jButton
    }

    /**
     * Initializes JButton retrieve, which on pressed, retrieves Duck DNS hosts.
     * @return JButton retrieve.
     */
    private fun initializeJButtonRetrieve(): JButton {
        val jButton = JButton("Retrieve")
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
    private fun initializeJButtonUpdate(): JButton {
        val jButton = JButton("Update")
        jButton.addActionListener {
            val selectedRow = jTable.selectedRow
            val oldDuckDnsSubdomain = duckDnsHosts[selectedRow]
            nextGraphicMenu = DuckDnsUpdateHostGraphicMenu(this, oldDuckDnsSubdomain)
            fireTableDataChanged()
            isVisible = false
        }
        return jButton
    }

    /**
     * Initializes JButton delete, which on pressed, deletes the selected Duck DNS host.
     * @return JButton delete.
     */
    private fun initializeJButtonDelete(): JButton {
        val jButton = JButton("Delete")
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
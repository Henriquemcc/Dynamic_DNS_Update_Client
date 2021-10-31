package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.defaultFont
import dynamic.dns.update.client.graphic.getConfirmation
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*

class DuckDnsHostMainGraphicMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    private val tableModel: DuckDnsSubdomainTableModel = DuckDnsSubdomainTableModel()
    private val jTable: JTable = initializeJTable()
    override val title: String = "Duck DNS subdomain menu"
    override val jFrame: JFrame = initializeJFrame()

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(FlowLayout())
        jPanel.add(initializeJPanelButtons())
        jPanel.add(initializeJScrollPaneTable())
        return jPanel
    }

    private fun initializeJPanelButtons(): JPanel {
        val jPanel = JPanel(GridLayout(4, 1))
        jPanel.add(initializeJButtonCreate())
        jPanel.add(initializeJButtonRetrieve())
        jPanel.add(initializeJButtonUpdate())
        jPanel.add(initializeJButtonDelete())
        return jPanel
    }

    private fun initializeJButtonCreate(): JButton {
        val jButton = JButton("Create")
        jButton.addActionListener {
            DuckDnsCreateHostGraphicMenu(this)
            isVisible = false
        }
        return jButton
    }

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

    private fun initializeJButtonDelete(): JButton {
        val jButton = JButton("Delete")
        jButton.addActionListener {
            val selectedRow = jTable.selectedRow
            val removedDuckDnsSubdomain = duckDnsHosts[selectedRow]
            if (getConfirmation("Would you like to remove $removedDuckDnsSubdomain?")) {
                HostsController.remove(removedDuckDnsSubdomain)
                fireTableDataChanged()
            }
        }
        return jButton
    }


    private fun initializeJScrollPaneTable(): JScrollPane {
        return JScrollPane(jTable)
    }

    private fun initializeJTable(): JTable {
        val jTable = JTable()
        jTable.font = defaultFont
        jTable.model = this.tableModel
        jTable.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        return jTable
    }

    fun fireTableDataChanged() {
        tableModel.fireTableDataChanged()
        jFrame.pack()
    }
}
package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.Menu
import dynamic.dns.update.client.graphic.defaultFont
import dynamic.dns.update.client.graphic.getConfirmation
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.*
import javax.swing.table.TableModel

class DuckDnsHostMainMenu(previousMenu: Menu? = null) : Menu(previousMenu) {
    override val jFrame: JFrame = initializeJFrame()
    private var jTable: JTable? = null
    private var tableModel: TableModel? = null
    override val title: String = "Duck DNS subdomain menu"

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
            DuckDnsCreateHostMenu(this)
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
            if (jTable != null) {
                val selectedRow = jTable!!.selectedRow
                val selectedHostname = jTable!!.getValueAt(selectedRow, 0) as String
                val oldDuckDnsSubdomain = duckDnsHosts.first { duckDnsSubdomain ->
                    duckDnsSubdomain.hostname.equals(selectedHostname)
                }
                nextMenu = DuckDnsUpdateHostMenu(this, oldDuckDnsSubdomain)
                fireTableDataChanged()
                isVisible = false
            }
        }
        return jButton
    }

    private fun initializeJButtonDelete(): JButton {
        val jButton = JButton("Delete")
        jButton.addActionListener {
            if (jTable != null) {
                val selectedRow = jTable!!.selectedRow
                val selectedHostname = jTable!!.getValueAt(selectedRow, 0) as String
                val removedDuckDnsSubdomain =
                    duckDnsHosts.first { duckDnsSubdomain ->
                        duckDnsSubdomain.hostname.equals(selectedHostname)
                    }
                if (getConfirmation("Would you like to remove $selectedHostname?")) {
                    HostsController.remove(removedDuckDnsSubdomain)
                }
                fireTableDataChanged()
            }
        }
        return jButton
    }


    private fun initializeJScrollPaneTable(): JScrollPane {
        val jScrollPane = JScrollPane()
        jScrollPane.add(initializeJTable())
        return jScrollPane
    }

    private fun initializeJTable(): JTable {
        val jTable = JTable()
        jTable.font = defaultFont
        jTable.model = initializeTableModel()
        jTable.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
        return jTable
    }

    private fun initializeTableModel(): TableModel {
        this.tableModel = DuckDnsSubdomainTableModel()
        return this.tableModel!!
    }

    private fun fireTableDataChanged() {
        (tableModel as? DuckDnsSubdomainTableModel)?.fireTableDataChanged()
    }
}
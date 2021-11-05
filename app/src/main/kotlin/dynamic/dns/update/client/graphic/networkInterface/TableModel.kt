package dynamic.dns.update.client.graphic.networkInterface

import javax.swing.table.AbstractTableModel

/**
 * Table model for network interfaces' name.
 * @param allowedNetworkInterfaces List of network interfaces' name which are already included in the list of allowed
 * network interfaces for this host.
 */
internal class TableModel(private val allowedNetworkInterfaces: MutableList<String>) : AbstractTableModel() {
    override fun getRowCount(): Int {
        return allowedNetworkInterfaces.size
    }

    override fun getColumnCount(): Int {
        return 1
    }

    override fun getValueAt(p0: Int, p1: Int): Any {
        return allowedNetworkInterfaces[p0]
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = false

    override fun getColumnName(column: Int): String {
        return "Network interface"
    }
}
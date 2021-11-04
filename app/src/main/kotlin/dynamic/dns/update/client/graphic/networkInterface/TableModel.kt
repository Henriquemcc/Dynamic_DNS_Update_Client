package dynamic.dns.update.client.graphic.networkInterface

import javax.swing.table.AbstractTableModel

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

    /**
     * Retrieves whether a cell is editable.
     * @param rowIndex Index of the row.
     * @param columnIndex Index of the column.
     * @return Boolean value of whether the cell is editable.
     */
    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = false

    override fun getColumnName(column: Int): String {
        return "Network interface"
    }
}
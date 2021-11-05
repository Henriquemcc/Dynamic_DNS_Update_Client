package dynamic.dns.update.client.graphic.duckdns

import javax.swing.table.AbstractTableModel

/**
 * Duck DNS subdomain host custom table model.
 */
internal class TableModel : AbstractTableModel() {

    /**
     * Retrieves the number of rows on the table.
     * @return Number of rows on the table.
     */
    override fun getRowCount(): Int = duckDnsHosts.size

    /**
     * Retrieves the number of columns on the table.
     * @return Number of columns on the table.
     */
    override fun getColumnCount(): Int = 5

    /**
     * Retrieves a value of a selected pair of column and row.
     * @param p0 Selected row.
     * @param p1 Selected column.
     * @return Value of a selected pair of column and row.
     */
    override fun getValueAt(p0: Int, p1: Int): Any {
        val duckDnsSubdomain = duckDnsHosts[p0]
        return when (p1) {
            0 -> duckDnsSubdomain.hostname
            1 -> duckDnsSubdomain.enableIPv4.toString()
            2 -> duckDnsSubdomain.enableIPv6.toString()
            3 -> duckDnsSubdomain.token
            4 -> duckDnsSubdomain.updateDelayTime.toString()
            else -> ""
        }
    }

    /**
     * Retrieves whether a cell is editable.
     * @param rowIndex Index of the row.
     * @param columnIndex Index of the column.
     * @return Boolean value of whether the cell is editable.
     */
    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean = false

    override fun getColumnName(column: Int): String {
        return when (column) {
            0 -> "Hostname"
            1 -> "Enable IPv4"
            2 -> "Enable IPv6"
            3 -> "Token"
            4 -> "Update delay time"
            else -> ""
        }
    }
}
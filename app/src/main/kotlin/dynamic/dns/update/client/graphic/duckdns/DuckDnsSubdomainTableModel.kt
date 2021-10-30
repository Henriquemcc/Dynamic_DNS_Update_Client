package dynamic.dns.update.client.graphic.duckdns

import javax.swing.table.AbstractTableModel

class DuckDnsSubdomainTableModel : AbstractTableModel() {

    override fun getRowCount(): Int {
        return duckDnsHosts.size
    }

    override fun getColumnCount(): Int {
        return 5
    }

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

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
        return false
    }

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
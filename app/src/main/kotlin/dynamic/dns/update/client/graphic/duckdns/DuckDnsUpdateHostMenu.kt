package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.graphic.Menu
import dynamic.dns.update.client.model.DuckDnsSubdomain
import javax.swing.JFrame

class DuckDnsUpdateHostMenu(previousMenu: Menu? = null, oldDuckDnsSubdomain: DuckDnsSubdomain) : Menu(previousMenu) {

    override val title: String = "Duck DNS update host menu"
    override val jFrame = initializeJFrame()

}
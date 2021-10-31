package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.Menu
import dynamic.dns.update.client.graphic.defaultFont
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.WindowEvent
import java.net.StandardProtocolFamily
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.swing.*

class DuckDnsUpdateHostMenu(previousMenu: Menu? = null, private val oldDuckDnsSubdomain: DuckDnsSubdomain) : Menu(previousMenu) {

    /// Token
    private fun initializeJTextFieldToken(): JTextField {
        val jTextFieldToken = JTextField(oldDuckDnsSubdomain.token)
        jTextFieldToken.font = defaultFont
        jTextFieldToken.isEditable = true
        return jTextFieldToken
    }

    private var jTextFieldToken: JTextField = initializeJTextFieldToken()
    /// End: Token

    /// Host
    private fun initializeJTextFieldHost(): JTextField {
        val jTextField = JTextField(oldDuckDnsSubdomain.hostname)
        jTextField.font = defaultFont
        jTextField.isEditable = true
        return jTextField
    }

    private var jTextFieldHost: JTextField = initializeJTextFieldHost()
    /// End: Host

    /// Enable IP protocol
    private fun initializeJCheckBoxEnableIPv4(): JCheckBox {
        val jCheckBox = JCheckBox("Enable IPv4", oldDuckDnsSubdomain.enableIPv4)
        jCheckBox.font = defaultFont
        return jCheckBox
    }

    private fun initializeJCheckBoxEnableIPv6(): JCheckBox {
        val jCheckBox = JCheckBox("Enable IPv6", oldDuckDnsSubdomain.enableIPv6)
        jCheckBox.font = defaultFont
        return jCheckBox
    }

    private fun initializeJCheckBoxsEnableIpProtocol(): HashMap<StandardProtocolFamily, JCheckBox> {
        val jCheckBox = HashMap<StandardProtocolFamily, JCheckBox>()
        jCheckBox[StandardProtocolFamily.INET] = initializeJCheckBoxEnableIPv4()
        jCheckBox[StandardProtocolFamily.INET6] = initializeJCheckBoxEnableIPv6()
        return jCheckBox
    }

    private val jCheckBoxIpProtocols : HashMap<StandardProtocolFamily, JCheckBox> = initializeJCheckBoxsEnableIpProtocol()
    /// End: Enable IP protocol

    /// Duration
    private fun initializeJTextFieldDelayDurationDays(): JTextField {
        val jTextField = JTextField((oldDuckDnsSubdomain.updateDelayTime.seconds / 60 / 60 / 24 ).toString())
        jTextField.font = defaultFont
        jTextField.isEditable = true
        return jTextField
    }

    private fun initializeJTextFieldDelayDurationHours(): JTextField {
        val jTextField = JTextField(((oldDuckDnsSubdomain.updateDelayTime.seconds / 60 / 60 ) % 24).toString())
        jTextField.font = defaultFont
        jTextField.isEditable = true
        return jTextField
    }

    private fun initializeJTextFieldDelayDurationMinutes(): JTextField {
        val jTextField = JTextField(((oldDuckDnsSubdomain.updateDelayTime.seconds / 60) % 60).toString())
        jTextField.font = defaultFont
        jTextField.isEditable = true
        return jTextField
    }

    private fun initializeJTextFieldDelayDurationSeconds(): JTextField {
        val jTextField = JTextField((oldDuckDnsSubdomain.updateDelayTime.seconds % 60).toString())
        jTextField.font = defaultFont
        jTextField.isEditable = true
        return jTextField
    }

    private fun initializeJTextFieldsDuration(): HashMap<TimeUnit, JTextField> {
        val jTextFields = HashMap<TimeUnit, JTextField>()
        jTextFields[TimeUnit.DAYS] = initializeJTextFieldDelayDurationDays()
        jTextFields[TimeUnit.HOURS] = initializeJTextFieldDelayDurationHours()
        jTextFields[TimeUnit.MINUTES] = initializeJTextFieldDelayDurationMinutes()
        jTextFields[TimeUnit.SECONDS] =  initializeJTextFieldDelayDurationSeconds()
        return jTextFields
    }

    private val jTextFieldsDuration: HashMap<TimeUnit, JTextField> = initializeJTextFieldsDuration()
    /// End: Duration

    /// Title
    override val title: String = "Duck DNS update host menu"
    /// End: Title

    /// JFrame
    //// JPanel
    ///// JPanel Delay Duration
    private fun initializeJTextFieldDelayDurationJLabel(): JLabel {
        return JLabel("Delay duration")
    }

    private fun initializeJPanelDelayDuration(): JPanel {
        val jPanelDelayDuration = JPanel(GridLayout(1, 4))
        jPanelDelayDuration.border = BorderFactory.createLineBorder(Color.black)
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationJLabel())
        jPanelDelayDuration.add(jTextFieldsDuration[TimeUnit.DAYS])
        jPanelDelayDuration.add(jTextFieldsDuration[TimeUnit.HOURS])
        jPanelDelayDuration.add(jTextFieldsDuration[TimeUnit.MINUTES])
        jPanelDelayDuration.add(jTextFieldsDuration[TimeUnit.SECONDS])
        return jPanelDelayDuration
    }
    ///// End: JPanel Delay Duration

    ///// JPanel IP Protocols CheckBox
    private fun initializeJPanelIpProtocolsCheckBox(): JPanel {
        val jPanelIPsCheckBox = JPanel(GridLayout(1, 2))
        jPanelIPsCheckBox.border = BorderFactory.createLineBorder(Color.black)
        jPanelIPsCheckBox.add(jCheckBoxIpProtocols[StandardProtocolFamily.INET])
        jPanelIPsCheckBox.add(jCheckBoxIpProtocols[StandardProtocolFamily.INET6])
        return jPanelIPsCheckBox
    }
    ///// End: JPanel IP Protocols CheckBox

    ///// JButton
    private fun initializeJButtonAdd(): JButton {
        val jButtonAdd = JButton("Modify")
        jButtonAdd.font = defaultFont
        jButtonAdd.isEnabled = true
        jButtonAdd.addActionListener {

            if (jTextFieldsDuration.all { it.value.text != null }) {
                val duration = Duration.ofDays(jTextFieldsDuration[TimeUnit.DAYS]!!.text.toLong()) +
                        Duration.ofHours(jTextFieldsDuration[TimeUnit.HOURS]!!.text.toLong()) +
                        Duration.ofMinutes(jTextFieldsDuration[TimeUnit.MINUTES]!!.text.toLong()) +
                        Duration.ofSeconds(jTextFieldsDuration[TimeUnit.SECONDS]!!.text.toLong())

                val host = DuckDnsSubdomain(
                    jTextFieldHost.text,
                    jCheckBoxIpProtocols[StandardProtocolFamily.INET]?.isSelected == true,
                    jCheckBoxIpProtocols[StandardProtocolFamily.INET6]?.isSelected == true, duration,
                    jTextFieldToken.text)

                HostsController.remove(oldDuckDnsSubdomain)
                HostsController.add(host)
                jFrame.dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
                (previousMenu as? DuckDnsHostMainMenu)?.fireTableDataChanged()
            }

        }
        return jButtonAdd
    }
    ///// End: JButton

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(5, 1))
        jPanel.add(jTextFieldHost)
        jPanel.add(jTextFieldToken)
        jPanel.add(initializeJPanelIpProtocolsCheckBox())
        jPanel.add(initializeJPanelDelayDuration())
        jPanel.add(initializeJButtonAdd())
        return jPanel
    }
    //// End: JPanel

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    override val jFrame = initializeJFrame()
    /// End: JFrame
}
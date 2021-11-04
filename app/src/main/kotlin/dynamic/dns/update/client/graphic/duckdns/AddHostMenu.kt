package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.GraphicMenu
import dynamic.dns.update.client.graphic.HintJTextField
import dynamic.dns.update.client.graphic.networkInterface.NetworkInterfaceMainMenu
import dynamic.dns.update.client.graphic.getDefaultFont
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.awt.Color
import java.awt.GridLayout
import java.awt.event.WindowEvent
import java.net.StandardProtocolFamily
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.swing.*

/**
 * Allows the user to create a Duck DNS host on the graphic user interface.
 * @param previousGraphicMenu Previous graphic menu to return after the execution of this menu.
 */
internal class AddHostMenu(previousGraphicMenu: GraphicMenu? = null) : GraphicMenu(previousGraphicMenu) {

    /**
     * Text field which will receive from the user the Duck DNS host's token.
     */
    private val jTextFieldToken: JTextField = initializeJTextFieldToken()

    /**
     * Text field which will receive from the user the Duck DNS host's hostname.
     */
    private var jTextFieldHostname: JTextField = initializeJTextFieldHostname()

    /**
     * Hash map of checkboxes which will receive from the user whether to enable or disable the IPs protocols.
     */
    private val jCheckBoxIpProtocols: HashMap<StandardProtocolFamily, JCheckBox> =
        initializeJCheckBoxsEnableIpProtocol()

    /**
     * Hash map of text fields which will store delay duration.
     */
    private val jTextFieldsDelayDuration: HashMap<TimeUnit, JTextField> = initializeJTextFieldsDelayDuration()

    private val allowedNetworkInterfaces: MutableList<String> = mutableListOf()

    override val title: String = "Duck DNS create host menu"

    override val jFrame: JFrame = initializeJFrame()

    /**
     * Initializes JTextField which will receive from the user the token.
     * @return JTextField which will receive the token.
     */
    private fun initializeJTextFieldToken(): JTextField {
        val jTextField = HintJTextField("Token")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes JTextField which will receive from the user the hostname.
     * @return JTextField which will receive the hostname.
     */
    private fun initializeJTextFieldHostname(): JTextField {
        val jTextField = HintJTextField("Hostname")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes JCheckBox which will receive from the user whether is to enable IPv4.
     * @return JCheckBox which will receive whether is to enable IPv4.
     */
    private fun initializeJCheckBoxEnableIPv4(): JCheckBox {
        val jCheckBox = JCheckBox("Enable IPv4", true)
        jCheckBox.font = getDefaultFont()
        return jCheckBox
    }

    /**
     * Initializes JCheckBox which will receive from the user whether is to enable IPv6.
     * @return JCheckBox which will receive whether is to enable IPv6.
     */
    private fun initializeJCheckBoxEnableIPv6(): JCheckBox {
        val jCheckBox = JCheckBox("Enable IPv6", true)
        jCheckBox.font = getDefaultFont()
        return jCheckBox
    }

    /**
     * Initializes the hash map which will store JCheckBox(es) enable IPv4 and enable IPv6.
     * @return Hash map with JCheckBoxes enable IPv4 and enable IPv6.
     */
    private fun initializeJCheckBoxsEnableIpProtocol(): HashMap<StandardProtocolFamily, JCheckBox> {
        val jCheckBox = HashMap<StandardProtocolFamily, JCheckBox>()
        jCheckBox[StandardProtocolFamily.INET] = initializeJCheckBoxEnableIPv4()
        jCheckBox[StandardProtocolFamily.INET6] = initializeJCheckBoxEnableIPv6()
        return jCheckBox
    }

    /**
     * Initializes JTextField delay time in days.
     * @return JTextField which will receive the number of days as a delay time.
     */
    private fun initializeJTextFieldDelayDurationDays(): JTextField {
        val jTextField = HintJTextField("Days")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes JTextField delay time in hours.
     * @return JTextField which will receive the number of hours as a delay time.
     */
    private fun initializeJTextFieldDelayDurationHours(): JTextField {
        val jTextField = HintJTextField("Hours")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes JTextField delay time in minutes.
     * @return JTextField which will receive the number of minutes as a delay time.
     */
    private fun initializeJTextFieldDelayDurationMinutes(): JTextField {
        val jTextField = HintJTextField("Minutes")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes JTextField delay time in seconds.
     * @return JTextField which will receive the number of seconds as a delay time.
     */
    private fun initializeJTextFieldDelayDurationSeconds(): JTextField {
        val jTextField = HintJTextField("Seconds")
        jTextField.font = getDefaultFont()
        jTextField.isEditable = true
        return jTextField
    }

    /**
     * Initializes the hash map which will store delay time JTextField(s).
     * @return Hash map which will store delay time JTextField(s).
     */
    private fun initializeJTextFieldsDelayDuration(): HashMap<TimeUnit, JTextField> {
        val jTextFields = HashMap<TimeUnit, JTextField>()
        jTextFields[TimeUnit.DAYS] = initializeJTextFieldDelayDurationDays()
        jTextFields[TimeUnit.HOURS] = initializeJTextFieldDelayDurationHours()
        jTextFields[TimeUnit.MINUTES] = initializeJTextFieldDelayDurationMinutes()
        jTextFields[TimeUnit.SECONDS] = initializeJTextFieldDelayDurationSeconds()
        return jTextFields
    }

    /**
     * Initializes delay duration title JLabel,
     * @return JLabel which will have delay duration title.
     */
    private fun initializeDelayDurationJLabel(): JLabel {
        return JLabel("Delay duration")
    }

    /**
     * Initializes JPanel which will have the graphic components of delay duration time.
     * @return JPanel with the graphic components of delay duration time.
     */
    private fun initializeJPanelDelayDuration(): JPanel {
        val jPanelDelayDuration = JPanel(GridLayout(1, 4))
        jPanelDelayDuration.border = BorderFactory.createLineBorder(Color.black)
        jPanelDelayDuration.add(initializeDelayDurationJLabel())
        jPanelDelayDuration.add(jTextFieldsDelayDuration[TimeUnit.DAYS])
        jPanelDelayDuration.add(jTextFieldsDelayDuration[TimeUnit.HOURS])
        jPanelDelayDuration.add(jTextFieldsDelayDuration[TimeUnit.MINUTES])
        jPanelDelayDuration.add(jTextFieldsDelayDuration[TimeUnit.SECONDS])
        return jPanelDelayDuration
    }

    /**
     * Initializes JPanel which will have the graphic components of enable / disable IP protocols.
     * @return JPanel with the graphic components of enable / disable IP protocols.
     */
    private fun initializeJPanelIpProtocolsCheckBox(): JPanel {
        val jPanelIPsCheckBox = JPanel(GridLayout(1, 2))
        jPanelIPsCheckBox.border = BorderFactory.createLineBorder(Color.black)
        jPanelIPsCheckBox.add(jCheckBoxIpProtocols[StandardProtocolFamily.INET])
        jPanelIPsCheckBox.add(jCheckBoxIpProtocols[StandardProtocolFamily.INET6])
        return jPanelIPsCheckBox
    }

    /**
     * Initializes JButton add.
     * @return JButton add.
     */
    private fun initializeJButtonAdd(): JButton {
        val jButton = JButton("Add")
        jButton.font = getDefaultFont()
        jButton.isEnabled = true
        jButton.addActionListener {

            if (jTextFieldsDelayDuration.all { it.value.text != null }) {
                val duration = Duration.ofDays((jTextFieldsDelayDuration[TimeUnit.DAYS] ?: return@addActionListener).text.toLong()) +
                        Duration.ofHours((jTextFieldsDelayDuration[TimeUnit.HOURS] ?: return@addActionListener).text.toLong()) +
                        Duration.ofMinutes((jTextFieldsDelayDuration[TimeUnit.MINUTES] ?: return@addActionListener).text.toLong()) +
                        Duration.ofSeconds((jTextFieldsDelayDuration[TimeUnit.SECONDS] ?: return@addActionListener).text.toLong())

                val host = DuckDnsSubdomain(
                    jTextFieldHostname.text,
                    jCheckBoxIpProtocols[StandardProtocolFamily.INET]?.isSelected == true,
                    jCheckBoxIpProtocols[StandardProtocolFamily.INET6]?.isSelected == true, duration,
                    jTextFieldToken.text
                )

                HostsController.add(host)
                jFrame.dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
                (previousGraphicMenu as? DuckDnsMainMenu)?.fireTableDataChanged()
            }

        }
        return jButton
    }

    /**
     * Initializes main JPanel.
     * @return Main JPanel.
     */
    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(6, 1))
        jPanel.add(jTextFieldHostname)
        jPanel.add(jTextFieldToken)
        jPanel.add(initializeJPanelIpProtocolsCheckBox())
        jPanel.add(initializeJPanelDelayDuration())
        jPanel.add(initializeJButtonNetworkInterfaceMenu())
        jPanel.add(initializeJButtonAdd())
        return jPanel
    }

    private fun initializeJButtonNetworkInterfaceMenu(): JButton {
        val jButton = JButton("Manage allowed network interfaces' name")

        jButton.font = getDefaultFont()
        jButton.isEnabled = true
        jButton.addActionListener{
            isVisible = false
            nextGraphicMenu = NetworkInterfaceMainMenu(this, allowedNetworkInterfaces)
        }

        return jButton
    }

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

}
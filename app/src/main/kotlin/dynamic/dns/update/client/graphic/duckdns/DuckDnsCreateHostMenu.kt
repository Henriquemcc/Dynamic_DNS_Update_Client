package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.graphic.HintJTextField
import dynamic.dns.update.client.graphic.Menu
import dynamic.dns.update.client.graphic.defaultFont
import dynamic.dns.update.client.model.DuckDnsSubdomain
import java.awt.Color
import java.awt.GridLayout
import java.net.StandardProtocolFamily
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.swing.*

class DuckDnsCreateHostMenu(previousMenu: Menu? = null) : Menu(previousMenu) {

    override val title: String = "Duck DNS create host menu"
    private var jTextFieldToken: JTextField? = null
    private var jTextFieldHost: JTextField? = null
    private val jCheckBoxIPs = HashMap<StandardProtocolFamily, JCheckBox>()
    private val jTextFieldsDuaration = HashMap<TimeUnit, JTextField>(6)
    override val jFrame = initializeJFrame()

    override fun initializeJFrame(): JFrame {
        val jFrame = super.initializeJFrame()
        jFrame.add(initializeJPanel())
        jFrame.pack()
        return jFrame
    }

    private fun initializeJPanel(): JPanel {
        val jPanel = JPanel(GridLayout(5, 1))
        jPanel.add(initializeJTextFieldHost())
        jPanel.add(initializeJTextFieldToken())
        jPanel.add(initializeJPanelIPsCheckBox())
        jPanel.add(initializeJPanelDelayDuration())
        jPanel.add(initializeJButtonAdd())
        return jPanel
    }

    private fun initializeJPanelIPsCheckBox(): JPanel {
        val jPanelIPsCheckBox = JPanel(GridLayout(1, 2))
        jPanelIPsCheckBox.border = BorderFactory.createLineBorder(Color.black)
        jPanelIPsCheckBox.add(initializeJCheckBoxEnableIPv6())
        jPanelIPsCheckBox.add(initializeJCheckBoxEnableIPv4())
        return jPanelIPsCheckBox
    }

    private fun initializeJPanelDelayDuration(): JPanel {
        val jPanelDelayDuration = JPanel(GridLayout(1, 6))
        jPanelDelayDuration.border = BorderFactory.createLineBorder(Color.black)
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationJLabel())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationDays())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationHours())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationMinutes())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationSeconds())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationMilliseconds())
        jPanelDelayDuration.add(initializeJTextFieldDelayDurationNanoSeconds())
        return jPanelDelayDuration
    }

    private fun initializeJTextFieldDelayDurationJLabel(): JLabel {
        return JLabel("Delay duration")
    }

    private fun initializeJTextFieldDelayDurationDays(): JTextField {
        jTextFieldsDuaration[TimeUnit.DAYS] = HintJTextField("Days")
        jTextFieldsDuaration[TimeUnit.DAYS]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.DAYS]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.DAYS] as JTextField
    }

    private fun initializeJTextFieldDelayDurationHours(): JTextField {
        jTextFieldsDuaration[TimeUnit.HOURS] = HintJTextField("Hours")
        jTextFieldsDuaration[TimeUnit.HOURS]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.HOURS]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.HOURS] as JTextField
    }

    private fun initializeJTextFieldDelayDurationMinutes(): JTextField {
        jTextFieldsDuaration[TimeUnit.MINUTES] = HintJTextField("Minutes")
        jTextFieldsDuaration[TimeUnit.MINUTES]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.MINUTES]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.MINUTES] as JTextField
    }

    private fun initializeJTextFieldDelayDurationSeconds(): JTextField {
        jTextFieldsDuaration[TimeUnit.SECONDS] = HintJTextField("Seconds")
        jTextFieldsDuaration[TimeUnit.SECONDS]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.SECONDS]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.SECONDS] as JTextField
    }

    private fun initializeJTextFieldDelayDurationMilliseconds(): JTextField {
        jTextFieldsDuaration[TimeUnit.MILLISECONDS] = HintJTextField("Milliseconds")
        jTextFieldsDuaration[TimeUnit.MILLISECONDS]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.MILLISECONDS]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.MILLISECONDS] as JTextField
    }

    private fun initializeJTextFieldDelayDurationNanoSeconds(): JTextField {
        jTextFieldsDuaration[TimeUnit.NANOSECONDS] = HintJTextField("Nano seconds")
        jTextFieldsDuaration[TimeUnit.NANOSECONDS]?.font = defaultFont
        jTextFieldsDuaration[TimeUnit.NANOSECONDS]?.isEditable = true
        return jTextFieldsDuaration[TimeUnit.NANOSECONDS] as JTextField
    }

    private fun initializeJButtonAdd(): JButton {
        val jButtonAdd = JButton("Add")
        jButtonAdd.font = defaultFont
        jButtonAdd.isEnabled = true
        jButtonAdd.addActionListener {

            if (jTextFieldHost != null && jTextFieldToken != null && jTextFieldsDuaration.all { it.value.text != null }) {
                val duration = Duration.ofDays(jTextFieldsDuaration[TimeUnit.DAYS]!!.text.toLong()) +
                        Duration.ofHours(jTextFieldsDuaration[TimeUnit.HOURS]!!.text.toLong()) +
                        Duration.ofMinutes(jTextFieldsDuaration[TimeUnit.MINUTES]!!.text.toLong()) +
                        Duration.ofSeconds(jTextFieldsDuaration[TimeUnit.SECONDS]!!.text.toLong()) +
                        Duration.ofMillis(jTextFieldsDuaration[TimeUnit.MILLISECONDS]!!.text.toLong()) +
                        Duration.ofNanos(jTextFieldsDuaration[TimeUnit.NANOSECONDS]!!.text.toLong())

                val host = DuckDnsSubdomain(jTextFieldHost!!.text,
                    jCheckBoxIPs[StandardProtocolFamily.INET]?.isEnabled == true,
                    jCheckBoxIPs[StandardProtocolFamily.INET6]?.isEnabled == true, duration,
                    jTextFieldToken!!.text)

                HostsController.add(host)
                jFrame.dispose()

            }

        }
        return jButtonAdd
    }

    private fun initializeJCheckBoxEnableIPv4(): JCheckBox {
        jCheckBoxIPs[StandardProtocolFamily.INET] = JCheckBox("Enable IPv4", true)
        jCheckBoxIPs[StandardProtocolFamily.INET]?.font = defaultFont
        return jCheckBoxIPs[StandardProtocolFamily.INET] as JCheckBox
    }

    private fun initializeJCheckBoxEnableIPv6(): JCheckBox {
        jCheckBoxIPs[StandardProtocolFamily.INET6] = JCheckBox("Enable IPv6", true)
        jCheckBoxIPs[StandardProtocolFamily.INET6]?.font = defaultFont
        return jCheckBoxIPs[StandardProtocolFamily.INET6] as JCheckBox
    }

    private fun initializeJTextFieldToken(): JTextField {
        jTextFieldToken = HintJTextField("Token")
        jTextFieldToken?.font = defaultFont
        jTextFieldToken?.isEditable = true
        return jTextFieldToken as JTextField
    }

    private fun initializeJTextFieldHost(): JTextField {
        jTextFieldHost = HintJTextField("Host")
        jTextFieldHost?.font = defaultFont
        jTextFieldHost?.isEditable = true
        return jTextFieldHost as JTextField
    }

}
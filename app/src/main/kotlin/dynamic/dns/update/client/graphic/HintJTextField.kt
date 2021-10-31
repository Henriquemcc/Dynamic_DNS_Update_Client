package dynamic.dns.update.client.graphic

import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

class HintJTextField(private val hint: String, private var showingHint: Boolean = true) : JTextField(hint),
    FocusListener {

    init {
        super.addFocusListener(this)
    }

    override fun focusGained(p0: FocusEvent?) {
        if (text.isEmpty()) {
            super.setText("")
            showingHint = false
        }
    }

    override fun focusLost(p0: FocusEvent?) {
        if (text.isEmpty()) {
            super.setText(hint)
            showingHint = true
        }
    }

    override fun getText(): String {
        return if (showingHint) "" else super.getText()
    }
}
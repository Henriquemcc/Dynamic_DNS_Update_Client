package dynamic.dns.update.client.graphic

import java.awt.event.FocusEvent
import java.awt.event.FocusListener
import javax.swing.JTextField

/**
 * A text field with hint.
 * @param hint Hint message.
 * @param showingHint Whether to show the hint message.
 */
class HintJTextField(private val hint: String, private var showingHint: Boolean = true) : JTextField(hint),
    FocusListener {

    /**
     * Initialization block.
     */
    init {
        super.addFocusListener(this)
    }

    /**
     * The action to do when the text field is clicked.
     */
    override fun focusGained(p0: FocusEvent?) {
        if (text.isEmpty()) {
            super.setText("")
            showingHint = false
        }
    }

    /**
     * The action to do when the text field is not clicked.
     */
    override fun focusLost(p0: FocusEvent?) {
        if (text.isEmpty()) {
            super.setText(hint)
            showingHint = true
        }
    }

    /**
     * Retrieves the text in the text field.
     * @return Text in the text filed.
     */
    override fun getText(): String {
        return if (showingHint) "" else super.getText()
    }
}
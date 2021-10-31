package dynamic.dns.update.client.graphic

import javax.swing.text.AttributeSet
import javax.swing.text.DocumentFilter

class IntegerDocumentFilter : DocumentFilter() {
    override fun insertString(fb: FilterBypass?, offset: Int, string: String?, attr: AttributeSet?) {
        val document = fb?.document
        val stringBuilder = StringBuilder()
        if (document != null) {
            stringBuilder.append(document.getText(0, document.length))
            stringBuilder.insert(offset, string)
        }

        if (test(stringBuilder.toString())) {
            super.insertString(fb, offset, string, attr)
        }
    }

    override fun replace(fb: FilterBypass?, offset: Int, length: Int, text: String?, attrs: AttributeSet?) {

        val document = fb?.document
        val stringBuilder = StringBuilder()
        if (document != null) {
            stringBuilder.append(document.getText(0, document.length))
            stringBuilder.replace(offset, offset + length, text)
        }

        if (test(stringBuilder.toString())) {
            super.replace(fb, offset, length, text, attrs)
        }
    }

    override fun remove(fb: FilterBypass?, offset: Int, length: Int) {

        val document = fb?.document
        val stringBuilder = StringBuilder()
        if (document != null) {
            stringBuilder.append(document.getText(0, document.length))
            stringBuilder.delete(offset, offset + length)
        }

        if (test(stringBuilder.toString())) {
            super.remove(fb, offset, length)
        }
    }

    private fun test(text: String): Boolean {
        return try {
            Integer.parseInt(text)
            true
        } catch (e: NumberFormatException) {
            false
        }
    }
}
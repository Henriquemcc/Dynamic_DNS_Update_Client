package dynamic.dns.update.client.international

import java.util.*

/**
 * Base name for resource bundle.
 */
private const val resourceBundleBaseName = "MessageBundle"

/**
 * Resource bundle where it will get language specific text.
 */
val resourceBundle: ResourceBundle
    get() {
        val country = System.getProperty("user.country")
        val language = System.getProperty("user.language")
        val locale = Locale(language, country)
        return ResourceBundle.getBundle(resourceBundleBaseName, locale, UTF8ResourceBundleControl)
    }
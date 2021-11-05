package dynamic.dns.update.client.international


import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

/**
 * UTF-8 Resource Bundle control object which will convert a resource bundle string to UTF-8.
 */
val UTF8ResourceBundleControl: ResourceBundle.Control = object : ResourceBundle.Control() {
    override fun newBundle(
        baseName: String,
        locale: Locale,
        format: String,
        loader: ClassLoader,
        reload: Boolean
    ): ResourceBundle {

        val bundleName = toBundleName(baseName, locale)
        val resourceName = toResourceName(bundleName, "properties")
        val inputStream: InputStream?
        var resourceBundle: ResourceBundle? = null
        if (reload) {
            val url = loader.getResource(resourceName)
            val urlConnection = url?.openConnection()
            urlConnection?.useCaches = false
            inputStream = urlConnection?.getInputStream()
        } else {
            inputStream = loader.getResourceAsStream(resourceName)
        }

        inputStream.use { inputStream1 ->
            inputStream1?.let { resourceBundle = PropertyResourceBundle(InputStreamReader(it, "UTF-8")) }
        }

        return resourceBundle!!
    }
}
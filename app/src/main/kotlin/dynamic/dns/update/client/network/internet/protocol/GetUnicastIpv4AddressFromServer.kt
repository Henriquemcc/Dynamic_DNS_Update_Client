package dynamic.dns.update.client.network.internet.protocol

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Inet4Address
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun getUnicastIPv4AddressFromServer(): Inet4Address? {
    val urlCheckIpAws = URL("https://checkip.amazonaws.com")
    val urlConnection = urlCheckIpAws.openConnection() as HttpsURLConnection
    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
    val message = bufferedReader.readLine()
    val ipAddress = Inet4Address.getByName(message)
    return ipAddress as? Inet4Address
}
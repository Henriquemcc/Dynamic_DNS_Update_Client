package dynamic.dns.update.client.console.common.myio

/**
 * Prints in a console the header of a menu.
 * @param title Header title.
 */
fun printHeader(title: String) {
    println("#".repeat(80))
    println(title)
    println("#".repeat(80))
    println()
}
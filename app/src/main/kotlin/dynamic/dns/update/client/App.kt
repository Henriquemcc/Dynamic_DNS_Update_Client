package dynamic.dns.update.client

import dynamic.dns.update.client.console.MainConsoleMenu
import dynamic.dns.update.client.graphic.MainGraphicMenu

fun main() {

    val thread1 = object : Thread() {
        override fun run() {
            MainGraphicMenu()
        }
    }


    val thread2 = object : Thread() {
        override fun run() {
            MainConsoleMenu()
        }
    }

    thread1.start()
    thread2.start()
}

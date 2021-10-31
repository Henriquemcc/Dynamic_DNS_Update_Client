package dynamic.dns.update.client.controller

import dynamic.dns.update.client.model.Host
import java.io.*
import java.nio.file.Paths

object HostsController : MutableList<Host> {

    private val file = Paths.get(System.getProperty("user.home"), ".dynamic_dns_ip_updater", "hosts").toFile()
    private var list = ArrayList<Host>()

    init {
        loadFromFile()
    }

    private fun createFile() {
        try {
            if (!file.parentFile.exists()) {
                file.parentFile.mkdirs()
            }
            if (!file.exists()) {
                file.createNewFile()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveToFile() {
        createFile()

        var fileOutputStream: FileOutputStream? = null
        var objectOutputStream: ObjectOutputStream? = null

        try {
            fileOutputStream = FileOutputStream(file)
            objectOutputStream = ObjectOutputStream(fileOutputStream)
            objectOutputStream.writeObject(list)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            objectOutputStream?.close()
            fileOutputStream?.close()
        }

    }

    private fun loadFromFile() {
        var fileInputStream: FileInputStream? = null
        var objectInputStream: ObjectInputStream? = null
        try {
            fileInputStream = FileInputStream(file)
            objectInputStream = ObjectInputStream(fileInputStream)
            val readObject = objectInputStream.readObject()
            if (readObject is ArrayList<*>) {
                list = readObject.filterIsInstance<Host>() as ArrayList<Host>
            }
            objectInputStream.close()
            fileInputStream.close()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            objectInputStream?.close()
            fileInputStream?.close()
        }
    }

    override val size: Int
        get() = list.size

    override fun contains(element: Host): Boolean {
        return list.contains(element)
    }

    override fun containsAll(elements: Collection<Host>): Boolean {
        return list.containsAll(elements)
    }

    override fun get(index: Int): Host {
        return list[index]
    }

    override fun indexOf(element: Host): Int {
        return list.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun iterator(): MutableIterator<Host> {
        return list.iterator()
    }

    override fun lastIndexOf(element: Host): Int {
        return list.lastIndexOf(element)
    }

    override fun add(element: Host): Boolean {
        var success = false
        if (list.add(element)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun add(index: Int, element: Host) {
        list.add(index, element)
        saveToFile()

    }

    override fun addAll(index: Int, elements: Collection<Host>): Boolean {
        var success = false
        if(list.addAll(index, elements)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun addAll(elements: Collection<Host>): Boolean {
        var success = false
        if(list.addAll(elements)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun clear() {
        list.clear()
        saveToFile()
    }

    override fun listIterator(): MutableListIterator<Host> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<Host> {
        return list.listIterator(index)
    }

    override fun remove(element: Host): Boolean {
        var success = false
        if (list.remove(element)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun removeAll(elements: Collection<Host>): Boolean {
        var success = false
        if (list.removeAll(elements)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun removeAt(index: Int): Host {
        val element = list.removeAt(index)
        saveToFile()
        return element
    }

    override fun retainAll(elements: Collection<Host>): Boolean {
        var success = false
        if (list.retainAll(elements)) {
            saveToFile()
            success = true
        }
        return success
    }

    override fun set(index: Int, element: Host): Host {
        val returnedElement = list.set(index, element)
        saveToFile()
        return returnedElement
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Host> {
        return list.subList(fromIndex, toIndex)
    }

    fun performUpdate(looping: Boolean = false) {
        for (host in list) {
            val thread = object : Thread() {
                override fun run() {
                    host.performUpdate(looping)
                }
            }
            thread.start()
        }
    }

    fun peformClean() {
        for (host in list) {
            val thread = object : Thread() {
                override fun run() {
                    host.performCleaning()
                }
            }
            thread.start()
        }
    }


}
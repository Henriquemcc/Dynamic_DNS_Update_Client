package io.github.henriquemcc.dduc.repository

import io.github.henriquemcc.dduc.model.DynamicDns
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

class DynamicDnsRepositoryImpl: DynamicDnsRepository {

    private val configFolder = Paths.get(System.getProperty("user.home"), ".dduc").toString()
    private val configFile = File(configFolder, "config.json")

    init {
        File(configFolder).mkdirs()
    }

    override fun save(dynamicDns: DynamicDns) {
        val dynamicDnsList = findAll().toMutableList()
        dynamicDnsList.add(dynamicDns)
        val json = Json.encodeToString(dynamicDnsList)
        configFile.writeText(json)
    }

    override fun findAll(): List<DynamicDns> {
        if (!configFile.exists())
            return emptyList()

        val json = configFile.readText()
        return Json.decodeFromString(json)
    }

    override fun findByDomain(domain: String): DynamicDns? {
        return findAll().find { it.domain == domain }
    }

    override fun delete(domain: String) {
        val dynamicDnsList = findAll().toMutableList()
        dynamicDnsList.removeIf { it.domain == domain }
        val json = Json.encodeToString(dynamicDnsList)
        configFile.writeText(json)
    }

}
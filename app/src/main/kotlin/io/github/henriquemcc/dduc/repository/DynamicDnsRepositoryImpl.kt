package io.github.henriquemcc.dduc.repository

import io.github.henriquemcc.dduc.model.DynamicDns
import io.github.henriquemcc.dduc.model.DuckDnsDynamicDns
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import java.io.File
import java.nio.file.Paths

class DynamicDnsRepositoryImpl: DynamicDnsRepository {

    private val configFolder = Paths.get(
        when {
            System.getProperty("os.name").lowercase().contains("win") -> System.getenv("ProgramData")
            System.getProperty("os.name").lowercase().contains("mac") -> "/Library/Application Support"
            else -> "/etc" // Default for Linux/Unix
        },
        "DynamicDnsUpdateClient"
    ).toString()
    private val configFile = File(configFolder, "config.json")

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        classDiscriminator = "_class" // Use a different discriminator name
        serializersModule = SerializersModule {
            polymorphic(DynamicDns::class) {
                subclass(DuckDnsDynamicDns::class)
                // Register other DynamicDns subclasses here as needed
            }
        }
    }

    init {
        File(configFolder).mkdirs()
    }

    override fun save(dynamicDns: DynamicDns) {
        val dynamicDnsList = findAll().toMutableList()
        dynamicDnsList.add(dynamicDns)
        val jsonString = json.encodeToString(dynamicDnsList)
        configFile.writeText(jsonString)
    }

    override fun findAll(): List<DynamicDns> {
        if (!configFile.exists())
            return emptyList()

        val jsonString = configFile.readText()
        return json.decodeFromString(jsonString)
    }

    override fun findByDomain(domain: String): DynamicDns? {
        return findAll().find { it.domain == domain }
    }

    override fun delete(domain: String) {
        val dynamicDnsList = findAll().toMutableList()
        dynamicDnsList.removeIf { it.domain == domain }
        val jsonString = json.encodeToString(dynamicDnsList)
        configFile.writeText(jsonString)
    }

    override fun findByType(type: String): List<DynamicDns> {
        return findAll().filter { it.type == type }
    }

}

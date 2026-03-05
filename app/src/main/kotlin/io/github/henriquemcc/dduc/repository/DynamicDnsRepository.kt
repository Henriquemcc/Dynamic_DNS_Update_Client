package io.github.henriquemcc.dduc.repository

import io.github.henriquemcc.dduc.model.DynamicDns

interface DynamicDnsRepository {
    fun save(dynamicDns: DynamicDns)
    fun findAll(): List<DynamicDns>
    fun findByDomain(domain: String): DynamicDns?
    fun delete(domain: String)
}
package io.github.henriquemcc.dduc.service

import io.github.henriquemcc.dduc.model.DynamicDns

interface DynamicDnsService {
    fun updateIpAddress(dynamicDns: DynamicDns): Boolean
    fun cleanIpAddress(dynamicDns: DynamicDns): Boolean
    fun testAuthentication(dynamicDns: DynamicDns): Boolean
}
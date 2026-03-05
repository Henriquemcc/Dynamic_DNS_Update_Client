package io.github.henriquemcc.dduc.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface DynamicDns {
    val type: String
    val domain: String
    var enableIpv4: Boolean
    var enableIpv6: Boolean
    var updateDelayTime: Long
    var retryDelayTime: Long
    var networkInterfaceNames: List<String>

    fun clone(): DynamicDns
}

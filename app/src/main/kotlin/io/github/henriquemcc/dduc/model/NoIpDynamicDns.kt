package io.github.henriquemcc.dduc.model

import kotlinx.serialization.Serializable

@Serializable
data class NoIpDynamicDns(
    override val type: String,
    override val domain: String,
    override var enableIpv4: Boolean,
    override var enableIpv6: Boolean,
    override var updateDelayTime: Long,
    override var retryDelayTime: Long,
    override var networkInterfaceNames: List<String>,
    var username: String,
    var password: String,
) : DynamicDns {
    override fun clone(): NoIpDynamicDns {
        return copy()
    }
}
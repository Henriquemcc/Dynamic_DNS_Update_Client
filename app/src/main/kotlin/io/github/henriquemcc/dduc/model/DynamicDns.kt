package io.github.henriquemcc.dduc.model

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
data class DynamicDns(
    val type: String,
    val domain: String,
    val enableIpv4: Boolean,
    val enableIpv6: Boolean,
    val updateDelayTime: Long,
    val retryDelayTime: Long,
    val token: String,
    val networkInterfaceNames: List<String>
)

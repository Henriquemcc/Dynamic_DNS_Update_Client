package io.github.henriquemcc.dduc.model

import java.time.Duration

data class DynamicDns(
    val type: String,
    val domain: String,
    val enableIpv4: Boolean,
    val enableIpv6: Boolean,
    val updateDelayTime: Duration,
    val retryDelayTime: Duration,
    val token: String,
    val networkInterfaceNames: List<String>
)

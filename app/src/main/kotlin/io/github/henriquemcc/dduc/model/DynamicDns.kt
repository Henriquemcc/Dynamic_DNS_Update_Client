package io.github.henriquemcc.dduc.model

import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
abstract class DynamicDns(
    val type: String,
    val domain: String,
    var enableIpv4: Boolean,
    var enableIpv6: Boolean,
    var updateDelayTime: Long,
    var retryDelayTime: Long,
    var networkInterfaceNames: List<String>
): Cloneable {
    public abstract override fun clone(): DynamicDns
}

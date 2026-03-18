package io.github.henriquemcc.dduc.model

import io.github.henriquemcc.dduc.util.toSha224
import kotlinx.serialization.Serializable

@Serializable
data class DynAddrToolsDynamicDns(
    override val type: String,
    override var enableIpv4: Boolean,
    override var enableIpv6: Boolean,
    override var updateDelayTime: Long,
    override var retryDelayTime: Long,
    override var networkInterfaceNames: List<String>,
    var secret: String,
    override val domain: String = "${secret.toSha224()}.dyn.addr.tools"
) : DynamicDns {

    override fun clone(): DynAddrToolsDynamicDns {
        return copy();
    }
}

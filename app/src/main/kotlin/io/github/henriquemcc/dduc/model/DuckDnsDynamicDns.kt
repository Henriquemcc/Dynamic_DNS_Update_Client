package io.github.henriquemcc.dduc.model

class DuckDnsDynamicDns(
    type: String,
    domain: String,
    enableIpv4: Boolean,
    enableIpv6: Boolean,
    updateDelayTime: Long,
    retryDelayTime: Long,
    networkInterfaceNames: List<String>,
    var token: String
): DynamicDns(
    type,
    domain,
    enableIpv4,
    enableIpv6,
    updateDelayTime,
    retryDelayTime,
    networkInterfaceNames
) {
    public override fun clone(): DuckDnsDynamicDns {
        return DuckDnsDynamicDns(
            type,
            domain,
            enableIpv4,
            enableIpv6,
            updateDelayTime,
            retryDelayTime,
            networkInterfaceNames,
            token
        )
    }
}
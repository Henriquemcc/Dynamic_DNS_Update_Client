package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain

val duckDnsHosts: List<DuckDnsSubdomain>
    get() = HostsController.filterIsInstance<DuckDnsSubdomain>()
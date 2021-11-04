package dynamic.dns.update.client.graphic.duckdns

import dynamic.dns.update.client.controller.HostsController
import dynamic.dns.update.client.model.DuckDnsSubdomain

/**
 * List of Duck DNS hosts.
 */
val duckDnsHosts: List<DuckDnsSubdomain>
    get() = HostsController.filterIsInstance<DuckDnsSubdomain>()
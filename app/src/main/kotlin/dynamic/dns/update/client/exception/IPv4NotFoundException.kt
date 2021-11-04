package dynamic.dns.update.client.exception

/**
 * Exception which is thrown when no IPv4 address has been found on the machine which this program is running.
 */
class IPv4NotFoundException : Exception("IPv4 address has not been found on this machine")
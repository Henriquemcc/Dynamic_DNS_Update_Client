package dynamic.dns.update.client.exception

/**
 * This exception is thrown when no IPv6 address has been found on the machine which this program is running.
 */
class IPv6NotFoundException : Exception("IPv6 address has not been found on this machine")
# Requirements

Description: A program that updates the IP address of a domain/subdomain of a provider of dynamic DNS.

## Functional Requirements

- The program must allow to Create, Retrieve, Update and Delete (CRUD) a dynamic DNS domain/subdomain object. This object is compose of the following attributes:
  - Domain/Subdomain: The hostname address of the dynamic DNS domain/subdomain.
  - enableIPv4: Whether to enable IPv4 update.
  - enableIPv6: Whether to enable IPv6 update.
  - updateDelayTime: The delay between each update of the dynamic DNS IP address.
  - retryDelayTime: The delay between a failed update and the next attempt.
  - Token: The dynamic dns provider authentication token.
  - networkInterfaceName: A list with the names of the network interfaces it will obtain the IP addresses.
- The program must run in background as a daemon to automatically update the IP addresses of each dynamic DNS domain/subdomain.
  - For each update of IP address for each domain it generates a detailed log.
- The update of the IP address of the subdomain must be compatible with both IPv4 and IPv6, allowing the user to disable any one of them.
  - To disable IPv4 or IPv6 the user must enable/disable the attributes enableIPv4 and enableIPv6.
- The program, for each dynamic dns domain/subdomain, must allow the user to choose which network interface name it will obtain its the IP addresses.
- The program, for each dynamic dns domain/subdomain, must securely store its token.
- The program, for each dynamic dns domain/subdomain, must allow the user to add a delay time to each domain/subdomain ip address update.
- The program must have a command to test the authentication and connectivity with the dynamic dns provider.

## Non-Functional Requirements

- The program must be written in Kotlin for Java Virtual Machine 8 with the framework Koin.
- The program must be built using Gradle KTS.
- There must be a CI/CD integration (with GitHub Actions) that checks the program and generates the artifacts when a new tag is created. The artifacts are:
  - A .jar portable executable of the program.
  - A .RPM installation package for RPM based distros.
  - A .DEB installation package for Debian based distros.
  - A .MSI installation package for Windows.
  - A .DMG installation package for MAC OS X.
- The program must be interfaced through command line, so that its procedures can be executed with scripts. The commands are:
  - dduc add <type> <domain/subdomain> <token> \[<enable_ipv4> <enable_ipv6> <delay_time>]
  - dduc list
  - dduc alter <type> <domain/subdomain> <attribute> <value>
  - dduc delete <type> <domain/subdomain>
  - dduc force-update \[<type> <domain/subdomain>]
  - dduc force-clean \[<type> <domain/subdomain>]
  - dduc test-auth \[<type> <domain/subdomain>]
  - dduc daemon
- The program must be developed in modules in a way that makes it possible the integration with other DNS providers (DynDNS, NO-IP, dyn.addr.tools, AWS Route 53, etc.) in the future. These modules could be developed as plugins, allowing the integration without having to change its core.
- The update of the IP address must be made asynchronously for each domain/subdomain, so that it happens independently, avoiding that erros in one domain/subdomain disturbs the update of other domain/subdomain.
- The communication with the API of the dynamic DNS providers must be secure (TLS 1.2 and 1.3).
- All dynamic dns objects will be store as JSON.
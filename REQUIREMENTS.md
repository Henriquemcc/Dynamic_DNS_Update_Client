# Requirements

Description: A program that updates the IP address of a domain/subdomain of a provider of dynamic DNS.

## Functional Requirements

- The program must allow to Create, Retrieve, Update and Delete (CRUD) a dynamic DNS domain/subdomain object. This object is compose of the following attributes:
  - Domain/Subdomain: The hostname address of the dynamic DNS domain/subdomain.
  - enableIPv4: Whether to enable IPv4 update.
  - enableIPv6: Whether to enable IPv6 update.
  - updateDelayTime: The delay between each update of the dynamic DNS IP address.
  - Token: The dynamic dns provider authentication token.
- The program must run in background as a daemon to automatically update the IP addresses of each dynamic DNS domain/subdomain. 
- The update of the IP address of the subdomain must be compatible with both IPv4 and IPv6, allowing the user to disable any one of them.
- The program, for each dynamic dns domain/subdomain, must allow the user to choose which network interface to obtain its the IP addresses.
- The program, for each dynamic dns domain/subdomain, must securely store its token.
- The program, for each dynamic dns domain/subdomain, must allow the user to add a delay time to each domain/subdomain ip address update. 

## Non-Functional Requirements

- The program must be written in Kotlin for Java Virtual Machine 8 with the framework Koin.
- The program must be built using Gradle KTS.
- The program must be interfaced through command line, so that its procedures can be executed with scripts.
- The program must be developed in modules in a way that makes it possible the integration with other DNS providers (DynDNS, NO-IP, dyn.addr.tools, AWS Route 53, etc.) in the future.
- The update of the IP address must be made asynchronously for each domain/subdomain, so that it happens independently, avoiding that erros in one domain/subdomain disturbs the update of other domain/subdomain.
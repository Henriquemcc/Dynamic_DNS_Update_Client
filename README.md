# Dynamic DNS Update Client

![GitHub License](https://img.shields.io/github/license/Henriquemcc/Dynamic_DNS_Update_Client)

A program that updates the IP address of a domain/subdomain of a provider of dynamic DNS.

## Features

### Main Features

- Domain/subdomain management (CRUD): A complete command line interface to add, list, alter and remove domain/subdomain configurations.
- Daemon mode: Background execution to automatically update the IP address of the domain/subdomain.
- Dual-Stack support: Both IPv4 and IPv6 updates are supported.
- Interface control: Allows the user to specify which network interface (ex: ```eth0```, ```wlan0```) the IP address should be extracted.
- Customized scheduling: Individual configuration of the delay time between updates and reattempt after failures.

### How it was built

- Stack: Developed in Kotlin (for JVM 17), using the framework Koin for dependency injection.
- Asynchronism: The updates of the IP address happen independently for each domain/subdomin, preventing an error in one domain/subdomain from locking up the others.   

### Delivery and Automation (DevOps)

- Build System: Uses Gradle KTS with customized tasks to the generation of the Fat-Jar and the installation packages.
- Multiplatform: Native support for the generation of installers in different formats:
  - Linux: ```.RPM```, ```.DEB```.
  - Windows: ```.MSI```.
- CI/CD: Integration with GitHub Actions that automates the generation of all artifacts when a new tag is created. 

## Download and Installation

To install this program, go to the [release page](https://github.com/Henriquemcc/Dynamic_DNS_Update_Client/releases) and download the installation package for your operating system.

## Usage

### Command Line

#### Showing the help menu

```dduc help [<type>]```

The optional arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider). The default value is ```null```.

Examples:

```dduc help```: Shows the main help menu

```dduc help DuckDns```: Shows the help menu for DuckDns domain/subdomain type.

```dduc help NoIp```: Shows the help menu for No-IP domains/subdomains type.

#### Adding a domain/subdomain

```dduc add <type> <domain/subdomain> <...> [<enableIpv4> <enableIpv6> <delayTime> <retryDelayTime>]```

The required arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider).

```<domain/subdomain>``` The domain/subdomain which you want to update its IP address.

```<...>``` Other required arguments for the type of tha domain/subdomain. For more information, type ```dduc help <type>```,

The optional arguments are:

```<enableIpv4>```: Whether to enable IPv6 IP update for this domain/subdomain. The default value is ```true```.

```<enableIpv6>```: Whether to enable IPv4 IP update for this domain/subdomain. The default value is ```true```.

```<delayTime>```: The amount of time in milliseconds to wait between each IP update. The default value is ```300000```.

```<retryDelayTime>```: The amount of time in milliseconds to wait to reattempt to update in case of failure. The default value is ```60000```.

##### Adding a DuckDns subdomain

```dduc add DuckDns <subdomain> <token> [<enableIpv4> <enableIpv6> <delayTime> <retryDelayTime>]```

The required arguments are:

```<subdomain>``` The subdomain which you want to update its IP address.

```<token>```: The Duck DNS authentication token.

The optional arguments are:

```<enableIpv4>```: Whether to enable IPv6 IP update for this domain/subdomain. The default value is ```true```.

```<enableIpv6>```: Whether to enable IPv4 IP update for this domain/subdomain. The default value is ```true```.

```<delayTime>```: The amount of time in milliseconds to wait between each IP update. The default value is ```300000```.

```<retryDelayTime>```: The amount of time in milliseconds to wait to reattempt to update in case of failure. The default value is ```60000```.

Example:

```dduc add DuckDns example.duckdns.org MySecretToken```

##### Adding a No-IP domain

```dduc add NoIp <domain> <username> <password> [<enableIpv4> <enableIpv6> <updateDelayTime> <retryDelayTime>]```

The required arguments are:

```<domain>```: The domain/subdomain which you want to update its IP address.

```<username>```: The No-IP account username.

```<password>```: The No-IP account password.

The optional arguments are:

```<enableIpv4>```: Whether to enable IPv6 IP update for this domain/subdomain. The default value is ```true```.

```<enableIpv6>```: Whether to enable IPv4 IP update for this domain/subdomain. The default value is ```true```.

```<delayTime>```: The amount of time in milliseconds to wait between each IP update. The default value is ```300000```.

```<retryDelayTime>```: The amount of time in milliseconds to wait to reattempt to update in case of failure. The default value is ```60000```.

Example:

```dduc add NoIp example.com myusername mypassword```: Adds a No-IP domain ```example.com``` with username ```myusername``` and password ```mypassword```.

#### Listing domains/subdomains

```dduc list [<type>]```

The optional arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider).

Examples:

```dduc list```: Lists all domains/subdomains.

```dduc list DuckDns```: Lists all DuckDns domains/subdomains.

```dduc list NoIp```: Lists all No-IP domains/subdomains.

#### Altering the value of a domain/subdomain attribute

```dduc alter <type> <domain/subdomain> <attribute> <value>```

The required arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider).

```<domain/subdomain>```: The domain/subdomain which you want to alter its attributes.

```<attribute>```: The attribute you want to alter its value.

```<value>```: The new value for the attribute.

Example:

```dduc alter DuckDns example.duckdns.org enableIpv4 false```: Changes the value of the attribute ```enableIpv4``` of the subdomain ```example.duckdns.org``` to ```false```.

#### Deleting a domain/subdomain

```dduc delete <type> <domain/subdomain>```

The required arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider).

```<domain/subdomain>```: The domain/subdomain which you want to delete.

Example:

```dduc delete DuckDns example.duckdns.org```: Deletes the subdomain ```example.duckdns.org```. 

#### Forcing the update of the IP address of a domain/subdomain

```dduc force-update [<type> <domain/subdomain>]```

The optional arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider) you want to force its update.

```<domain/subdomain>```: The domain/subdomain which you want to force its update.

Examples:

```dduc force-update```: Forces the update of all domains/subdomains.

```dduc force-update DuckDns```: Forces the update of all DuckDns subdomains.

```dduc force-update DuckDns example.duckdns.org```: Forces the update of the DuckDns subdomain ```example.duckdns.org```.

#### Forcing the cleaning (set to NULL or 0.0.0.0, ::0) of the IP address of a domain/subdomain

```dduc force-clean [<type> <domain/subdomain>]```

The optional arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider) you want to force its cleaning.

```<domain/subdomain>```: The domain/subdomain which you want to force its cleaning.

Examples:

```dduc force-clean```: Forces the cleaning of all domains/subdomains.

```dduc force-clean DuckDns```: Forces the cleaning of all DuckDns subdomains.

```dduc force-clean DuckDns example.duckdns.org```: Forces the cleaning of the DuckDns subdomain ```example.duckdns.org```.

#### Testing the authentication with the Dynamic DNS Provider

```dduc test-auth [<type> <domain/subdomain>]```

The optional arguments are:

```<type>```: The type of the domain/subdomain (the Dynamic DNS Provider) you want to test its authentication

```<domain/subdomain>```: The domain/subdomain which you want to test its authentication.

Examples:

```dduc test-auth```: Tests the authentication of all domains/subdomains.

```dduc test-auth DuckDns```: Tests the authentication of all DuckDns domains/subdomains.

```dduc test-auth DuckDns example.duckdns.org```: Tests the authentication of the DuckDns subdomain ```example.duckdns.org```.

#### Running as a daemon

```dduc daemon```

## Contributing

For more information about contributing, read the [CONTRIBUTING.md](CONTRIBUTING.md) and the [REQUIREMENTS.md](REQUIREMENTS.md).

## License

This program is licensed under [European Union Public License 1.2](LICENSE).
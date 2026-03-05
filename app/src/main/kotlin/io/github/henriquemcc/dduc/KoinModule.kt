package io.github.henriquemcc.dduc

import io.github.henriquemcc.dduc.cli.DuckDnsCli
import io.github.henriquemcc.dduc.cli.DynamicDnsCli
import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.repository.DynamicDnsRepositoryImpl
import io.github.henriquemcc.dduc.service.DynamicDnsService
import io.github.henriquemcc.dduc.service.DynamicDnsServiceImpl
import io.github.henriquemcc.dduc.service.NetworkService
import io.github.henriquemcc.dduc.service.NetworkServiceImpl
import io.github.henriquemcc.dduc.provider.DynamicDnsProvider
import io.github.henriquemcc.dduc.provider.DuckDnsProvider
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {
    single<DynamicDnsRepository> { DynamicDnsRepositoryImpl() }
    single<NetworkService> { NetworkServiceImpl() }
    single<DynamicDnsProvider> { DuckDnsProvider() } // Register GenericDynamicDnsProvider

    // Register individual DynamicDnsCli implementations
    singleOf(::DuckDnsCli) { bind<DynamicDnsCli>() }
    // If you had other implementations, you'd add them here:
    // singleOf(::AnotherDnsCli) { bind<DynamicDnsCli>() }

    // Explicitly provide the List<DynamicDnsCli> by collecting all registered DynamicDnsCli
    single<List<DynamicDnsCli>> { getAll() }

    single<DynamicDnsService> { DynamicDnsServiceImpl(getAll(), get()) } // Inject all DynamicDnsProviders
}

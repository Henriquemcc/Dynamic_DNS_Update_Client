package io.github.henriquemcc.dduc

import io.github.henriquemcc.dduc.repository.DynamicDnsRepository
import io.github.henriquemcc.dduc.repository.DynamicDnsRepositoryImpl
import io.github.henriquemcc.dduc.service.DynamicDnsService
import io.github.henriquemcc.dduc.service.DynamicDnsServiceImpl
import io.github.henriquemcc.dduc.service.NetworkService
import io.github.henriquemcc.dduc.service.NetworkServiceImpl
import io.github.henriquemcc.dduc.provider.DynamicDnsProvider
import io.github.henriquemcc.dduc.provider.DuckDnsProvider
import org.koin.dsl.module

val koinModule = module {
    single<DynamicDnsRepository> { DynamicDnsRepositoryImpl() }
    single<NetworkService> { NetworkServiceImpl() }
    factory<DynamicDnsProvider> { DuckDnsProvider() } // Register GenericDynamicDnsProvider
    single<DynamicDnsService> { DynamicDnsServiceImpl(getAll(), get()) } // Inject all DynamicDnsProviders
}
package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.data.repository.CompanyNetworkRepository
import com.manualcheg.ktscourse.data.repository.HistoryNetworkRepository
import com.manualcheg.ktscourse.data.repository.LaunchNetworkRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.data.repository.RocketNetworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(HttpTimeout){
                requestTimeoutMillis = 3000
                connectTimeoutMillis = 3000
                socketTimeoutMillis = 3000
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                        explicitNulls = false
                    },
                )
            }
        }
    }
    single { NetworkRepository(get()) }.apply {
        bind<LaunchNetworkRepository>()
        bind<RocketNetworkRepository>()
        bind<HistoryNetworkRepository>()
        bind<CompanyNetworkRepository>()
    }
}

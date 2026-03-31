package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    },
                )
            }
        }
    }
    singleOf(::NetworkRepositoryImpl) bind NetworkRepository::class
}

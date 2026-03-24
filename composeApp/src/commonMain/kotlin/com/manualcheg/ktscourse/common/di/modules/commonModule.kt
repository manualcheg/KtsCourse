package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.data.database.AppDatabase
import com.manualcheg.ktscourse.data.database.getAppDatabase
import com.manualcheg.ktscourse.data.database.getDatabaseBuilder
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.DatabaseRepositoryImpl
import com.manualcheg.ktscourse.data.repository.LaunchRepositoryImpl
import com.manualcheg.ktscourse.data.repository.NetworkRepository
import com.manualcheg.ktscourse.data.repository.NetworkRepositoryImpl
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepositoryImpl
import com.manualcheg.ktscourse.screenMain.domain.repository.LaunchRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    // Database
    single { getAppDatabase(getDatabaseBuilder()) }
    single { get<AppDatabase>().launchDao() }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }

    // Network
    single<NetworkRepository> { NetworkRepositoryImpl() }

    // DataStore & Preferences
    single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(get()) }
}

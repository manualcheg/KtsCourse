package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.data.database.AppDatabase
import com.manualcheg.ktscourse.data.database.getAppDatabase
import com.manualcheg.ktscourse.data.database.getDatabaseBuilder
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.data.repository.DatabaseRepositoryImpl
import com.manualcheg.ktscourse.data.repository.UserPreferencesRepositoryImpl
import org.koin.dsl.module

val commonModule =
    module {
        // Database
        single { getAppDatabase(getDatabaseBuilder()) }
        single { get<AppDatabase>().launchDao() }
        single { get<AppDatabase>().rocketDao() }
        single { get<AppDatabase>().favoriteDao() }
        single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }

        // DataStore & Preferences
        single<UserPreferencesRepository> { UserPreferencesRepositoryImpl(get()) }
    }

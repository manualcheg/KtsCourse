package com.manualcheg.ktscourse.common.di.modules

import com.manualcheg.ktscourse.screenFavorites.data.FavoritesRepositoryImpl
import com.manualcheg.ktscourse.screenFavorites.domain.repository.FavoritesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favoritesModule =
    module {
        singleOf(::FavoritesRepositoryImpl) bind FavoritesRepository::class
    }

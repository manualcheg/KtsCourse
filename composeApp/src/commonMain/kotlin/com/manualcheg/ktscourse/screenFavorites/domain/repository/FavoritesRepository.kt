package com.manualcheg.ktscourse.screenFavorites.domain.repository

import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket

import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getAllFavoritesLaunches(): Flow<List<Launch>>
    fun getAllFavoritesRockets(): Flow<List<Rocket>>
}

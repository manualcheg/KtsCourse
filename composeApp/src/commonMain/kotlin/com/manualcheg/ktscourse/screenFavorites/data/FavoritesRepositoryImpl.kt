package com.manualcheg.ktscourse.screenFavorites.data

import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.screenFavorites.domain.repository.FavoritesRepository
import com.manualcheg.ktscourse.domain.model.Launch
import com.manualcheg.ktscourse.screenRockets.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

class FavoritesRepositoryImpl(
    private val databaseRepository: DatabaseRepository,
) : FavoritesRepository {
    override fun getAllFavoritesLaunches(): Flow<List<Launch>> = databaseRepository.getAllFavoriteLaunches()
    override fun getAllFavoritesRockets(): Flow<List<Rocket>> = databaseRepository.getAllFavoriteRockets()
}

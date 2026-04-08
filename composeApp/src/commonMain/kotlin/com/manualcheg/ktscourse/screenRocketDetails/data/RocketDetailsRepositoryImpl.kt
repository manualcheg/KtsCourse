package com.manualcheg.ktscourse.screenRocketDetails.data

import com.manualcheg.ktscourse.data.mappers.toDomainDetails
import com.manualcheg.ktscourse.data.mappers.toEntity
import com.manualcheg.ktscourse.data.database.dao.FavoriteDao
import com.manualcheg.ktscourse.data.repository.RocketNetworkRepository
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetails
import com.manualcheg.ktscourse.screenRocketDetails.domain.RocketDetailsRepository
import io.github.aakira.napier.Napier

class RocketDetailsRepositoryImpl(
    private val rocketNetworkRepository: RocketNetworkRepository,
    private val favoriteDao: FavoriteDao
) : RocketDetailsRepository {

    override suspend fun getRocketDetails(rocketId: String): Result<RocketDetails> {
        return try {
            val favoriteRocket = favoriteDao.getFavoriteRocket(rocketId)
            if (favoriteRocket != null) {
                return Result.success(favoriteRocket.toDomainDetails())
            }

            val rocketDtoResult = rocketNetworkRepository.getRocket(rocketId)
            if (rocketDtoResult.isSuccess) {
                Result.success(rocketDtoResult.getOrThrow().toDomainDetails(isFavorite = false))
            } else {
                Result.failure(rocketDtoResult.exceptionOrNull() ?: Exception("Rocket not found"))
            }
        } catch (e: Exception) {
            Napier.e("Error getting rocket details", e)
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(rocketDetails: RocketDetails): Result<Boolean> {
        return try {
            val isCurrentlyFavorite = favoriteDao.isRocketFavorite(rocketDetails.id)
            if (isCurrentlyFavorite) {
                favoriteDao.deleteFavoriteRocket(rocketDetails.id)
                Result.success(false)
            } else {
                favoriteDao.insertFavoriteRocket(rocketDetails.toEntity())
                Result.success(true)
            }
        } catch (e: Exception) {
            Napier.e("Error toggling favorite", e)
            Result.failure(e)
        }
    }
}

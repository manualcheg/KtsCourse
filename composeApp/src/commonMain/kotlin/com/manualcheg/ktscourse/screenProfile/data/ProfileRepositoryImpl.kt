package com.manualcheg.ktscourse.screenProfile.data

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.data.repository.DatabaseRepository
import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import com.manualcheg.ktscourse.screenProfile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val databaseRepository: DatabaseRepository,
) : ProfileRepository {
    override fun getData(): Flow<UserData> = userPreferencesRepository.userData

    override suspend fun logout() {
        userPreferencesRepository.clearUserData()
        databaseRepository.deleteAllLaunches()
    }
}

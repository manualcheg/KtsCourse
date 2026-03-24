package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.common.repository.UserPreferencesRepository
import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import kotlinx.coroutines.flow.Flow

class GetUserDataUseCaseImpl(private val userPreferencesRepository: UserPreferencesRepository) :
    GetUserDataUseCase {
    override fun execute(): Flow<UserData> {
        return userPreferencesRepository.userData
    }
}

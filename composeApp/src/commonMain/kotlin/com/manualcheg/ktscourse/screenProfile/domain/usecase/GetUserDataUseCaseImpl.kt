package com.manualcheg.ktscourse.screenProfile.domain.usecase

import com.manualcheg.ktscourse.screenLogin.domain.model.UserData
import com.manualcheg.ktscourse.screenProfile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetUserDataUseCaseImpl(private val profileRepository: ProfileRepository) :
    GetUserDataUseCase {
    override fun execute(): Flow<UserData> {
        return profileRepository.getData()
    }
}

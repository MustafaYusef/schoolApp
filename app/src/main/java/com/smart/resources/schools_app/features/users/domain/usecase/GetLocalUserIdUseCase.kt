package com.smart.resources.schools_app.features.users.domain.usecase

import com.smart.resources.schools_app.features.users.data.repository.UserRepository
import javax.inject.Inject

class GetLocalUserIdUseCase @Inject constructor(private val userRepository: UserRepository) :
    IGetCurrentLocalUserIdUseCase {
    override suspend fun invoke(): String {
        return userRepository.getCurrentAccount()?.userId.toString()
    }

}
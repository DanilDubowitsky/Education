package com.testeducation.domain.cases.user

import com.testeducation.domain.model.user.UserStatistics
import com.testeducation.domain.repository.user.IUserRepository

class GetUserStatistics(private val userRepository: IUserRepository) {
    suspend operator fun invoke(): UserStatistics = userRepository.getUserStatistics()
}
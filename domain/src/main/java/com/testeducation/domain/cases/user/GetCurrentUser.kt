package com.testeducation.domain.cases.user

import com.testeducation.domain.model.user.User
import com.testeducation.domain.repository.user.IUserRepository

class GetCurrentUser(
    private val userRepository: IUserRepository
) {

    suspend operator fun invoke(): User = userRepository.getCurrentUser()
}

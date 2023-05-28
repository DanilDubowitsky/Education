package com.example.domain.cases.user

import com.example.domain.model.user.User
import com.example.domain.repository.user.IUserRepository

class GetCurrentUser(
    private val userRepository: IUserRepository
) {

    suspend operator fun invoke(): User = userRepository.getCurrentUser()
}

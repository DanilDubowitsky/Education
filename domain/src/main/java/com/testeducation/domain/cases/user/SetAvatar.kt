package com.testeducation.domain.cases.user

import com.testeducation.domain.repository.user.IUserRepository

class SetAvatar(private val userRepository: IUserRepository) {
    suspend operator fun invoke(avatarId: Int): Unit = userRepository.setAvatar(avatarId = avatarId)
}
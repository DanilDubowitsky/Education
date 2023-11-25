package com.testeducation.domain.cases.user

import com.testeducation.domain.model.profile.CategorySupport
import com.testeducation.domain.repository.user.IUserRepository

class SendSupport(private val userRepository: IUserRepository) {
    suspend operator fun invoke(text: String, categorySupport: CategorySupport): Unit =
        userRepository.sendSupport(text = text, category = categorySupport.toString())
}
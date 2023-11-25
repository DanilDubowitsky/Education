package com.testeducation.screen.profile

import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserStatistics

data class ProfileModelState(
    val user: User? = null,
    val userStatistics: UserStatistics? = null
)
package com.testeducation.domain.cases.user

import com.testeducation.domain.config.user.IUserConfig

class IsVisibleAvatar(private val userConfig: IUserConfig) {
    operator fun invoke(): Boolean = userConfig.getAvatarVisibleScreen()
}
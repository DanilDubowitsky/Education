package com.testeducation.domain.cases.user

import com.testeducation.domain.config.user.IUserConfig

class SetVisibleAvatar(private val userConfig: IUserConfig) {
    operator fun invoke(visible: Boolean) = userConfig.setAvatarVisibleScreen(visible)
}
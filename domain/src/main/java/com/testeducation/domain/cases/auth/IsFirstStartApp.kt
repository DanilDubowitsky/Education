package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig

class IsFirstStartApp(
    private val userConfig: IUserConfig
) {
    operator fun invoke(): Boolean {
        return userConfig.isFirstStartApp()
    }
}
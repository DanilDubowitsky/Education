package com.testeducation.domain.cases.auth

import com.testeducation.domain.config.user.IUserConfig

class SetFirstStartApp(
    private val userConfig: IUserConfig,
) {
    operator fun invoke(value: Boolean = false) {
        userConfig.setFirstStartApp(value)
    }
}
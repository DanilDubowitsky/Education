package com.testeducation.domain.cases.internal

import com.testeducation.domain.config.internal.IAppVersionConfig

class IsAppVersionUpdateRequired(
    private val config: IAppVersionConfig
) {

    suspend operator fun invoke() = config.isUpdateRequire()
}

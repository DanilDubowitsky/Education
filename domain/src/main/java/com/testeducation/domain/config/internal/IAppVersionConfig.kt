package com.testeducation.domain.config.internal

interface IAppVersionConfig {

    suspend fun isUpdateRequire(): Boolean

}

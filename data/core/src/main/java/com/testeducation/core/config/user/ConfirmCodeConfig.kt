package com.testeducation.core.config.user

import com.testeducation.core.config.IConfigSource
import com.testeducation.domain.config.user.IConfirmCodeConfig

class ConfirmCodeConfig(private val configSource: IConfigSource): IConfirmCodeConfig {

    companion object {
        const val CONFIRM_CODE_TIME = "CONFIRM_CODE_TIME"
        const val NAME = "ConfirmCodeConfig"
    }

    override val defaultTime: Long
        get() = -1L

    override fun setTime(time: Long) {
        configSource.setLong(CONFIRM_CODE_TIME, time)
    }

    override fun getTime(): Long {
        return configSource.getLong(CONFIRM_CODE_TIME, defaultTime)
    }
}
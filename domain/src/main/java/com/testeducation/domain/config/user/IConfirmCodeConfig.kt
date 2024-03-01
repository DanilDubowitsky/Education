package com.testeducation.domain.config.user

interface IConfirmCodeConfig {
    val defaultTime : Long

    fun setTime(time: Long)
    fun getTime(): Long
}
package com.testeducation.domain.config.user

interface ITokenConfirmConfig {
    fun set(token: String, email: String)

    fun get(): String

    fun getEmail(): String
}
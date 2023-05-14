package com.example.remote.converter.auth

import com.example.domain.model.auth.Token
import com.example.remote.model.auth.RemoteToken

fun RemoteToken.toModel(): Token = Token(
    accessToken,
    refreshToken
)

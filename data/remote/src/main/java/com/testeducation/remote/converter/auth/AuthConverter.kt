package com.testeducation.remote.converter.auth

import com.testeducation.domain.model.auth.Token
import com.testeducation.remote.model.auth.RemoteToken

fun RemoteToken.toModel(): Token = Token(
    accessToken,
    refreshToken
)

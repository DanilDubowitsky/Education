package com.testeducation.remote.converter.user

import com.testeducation.domain.model.user.User
import com.testeducation.remote.model.user.RemoteUser

fun RemoteUser.toModel() = User(
    id,
    email,
    userName,
    registryDate,
    roles
)

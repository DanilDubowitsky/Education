package com.example.remote.converter.user

import com.example.domain.model.user.User
import com.example.remote.model.user.RemoteUser

fun RemoteUser.toModel() = User(
    id,
    email,
    userName,
    registryDate,
    roles
)

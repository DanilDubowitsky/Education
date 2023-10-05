package com.testeducation.remote.converter.user

import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserInfo
import com.testeducation.remote.model.user.RemoteUser
import com.testeducation.remote.model.user.RemoteUserInfo

fun RemoteUser.toModel() = User(
    id,
    email,
    userName,
    registryDate,
    roles
)

fun RemoteUserInfo.toModel() = UserInfo(
    id,
    username
)

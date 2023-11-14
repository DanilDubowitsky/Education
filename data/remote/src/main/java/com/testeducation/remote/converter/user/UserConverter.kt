package com.testeducation.remote.converter.user

import com.testeducation.domain.model.user.User
import com.testeducation.domain.model.user.UserInfo
import com.testeducation.domain.model.user.UserStatistics
import com.testeducation.remote.model.user.RemoteUser
import com.testeducation.remote.model.user.RemoteUserInfo
import com.testeducation.remote.model.user.RemoteUserStatistics

fun RemoteUser.toModel() = User(
    id,
    email,
    userName,
    registryDate,
    roles
)

fun RemoteUserStatistics.userStatisticsToModel() = UserStatistics(
    createdTestCount = createdTestCount ?: 0, passedTestCount = passedTestCount ?: 0
)

fun RemoteUserInfo.toModel() = UserInfo(
    id,
    username
)

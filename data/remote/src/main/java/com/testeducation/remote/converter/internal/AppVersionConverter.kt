package com.testeducation.remote.converter.internal

import com.testeducation.domain.model.internal.AppVersion
import com.testeducation.remote.model.internal.RemoteAppVersion

fun RemoteAppVersion.toModel() = AppVersion(
    actualVersion,
    minVersion
)

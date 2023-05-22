package com.example.remote.converter.test

import com.example.domain.model.test.ThemeShort
import com.example.remote.model.test.RemoteThemeShort

fun RemoteThemeShort.toModel() = ThemeShort(
    id,
    title
)

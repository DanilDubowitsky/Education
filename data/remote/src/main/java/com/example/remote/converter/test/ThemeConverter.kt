package com.example.remote.converter.test

import com.example.domain.model.theme.ThemeOrderField
import com.example.domain.model.theme.ThemeShort
import com.example.remote.model.test.RemoteThemeShort

private const val TITLE = "title"
private const val CREATION = "creation"

fun RemoteThemeShort.toModel() = ThemeShort(
    id,
    title
)

fun List<RemoteThemeShort>.toModels() = this.map(RemoteThemeShort::toModel)

fun ThemeOrderField.toRemote(): String = when (this) {
    ThemeOrderField.TITLE -> TITLE
    ThemeOrderField.CREATION -> CREATION
}

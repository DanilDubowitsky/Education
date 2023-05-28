package com.testeducation.remote.converter.test

import com.testeducation.domain.model.theme.ThemeOrderField
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.remote.model.test.RemoteThemeShort

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

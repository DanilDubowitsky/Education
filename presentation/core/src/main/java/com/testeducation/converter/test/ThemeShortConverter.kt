package com.testeducation.converter.test

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.theme.ThemeShortUI

fun ThemeShort.toUI(selectedThemeId: String? = null) = ThemeShortUI(
    id,
    title,
    id == selectedThemeId
)

fun List<ThemeShort>.toUIModels(selectedThemeId: String? = null) = this.map { theme ->
    theme.toUI(selectedThemeId)
}

fun ThemeShortUI.toModel() = ThemeShort(id, title)

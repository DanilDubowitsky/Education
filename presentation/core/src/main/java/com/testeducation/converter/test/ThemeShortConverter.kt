package com.testeducation.converter.test

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.theme.ThemeShortUI

fun ThemeShort.toUI(selectedThemeId: String? = null) = ThemeShortUI(
    id,
    title,
    id == selectedThemeId
)

fun ThemeShort.toUISelected(isSelected: Boolean = true) = ThemeShortUI(
    id,
    title,
    isSelected = isSelected
)

fun List<ThemeShort>.toUIModels(selectedThemeId: String? = null) = this.map { theme ->
    theme.toUI(selectedThemeId)
}

fun ThemeShortUI.toModel() = ThemeShort(id, title)

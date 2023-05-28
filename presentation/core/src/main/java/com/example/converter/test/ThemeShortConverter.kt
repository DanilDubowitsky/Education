package com.example.converter.test

import com.example.domain.model.theme.ThemeShort
import com.example.logic.model.theme.ThemeShortUI

fun ThemeShort.toUI(selectedThemeId: String? = null) = ThemeShortUI(
    id,
    title,
    id == selectedThemeId
)

fun List<ThemeShort>.toUIModels(selectedThemeId: String? = null) = this.map { theme ->
    theme.toUI(selectedThemeId)
}

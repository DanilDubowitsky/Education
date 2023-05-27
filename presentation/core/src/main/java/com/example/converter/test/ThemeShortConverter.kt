package com.example.converter.test

import com.example.domain.model.theme.ThemeShort
import com.example.logic.model.theme.ThemeShortUI

fun ThemeShort.toUI() = ThemeShortUI(
    id,
    title
)

fun List<ThemeShort>.toUIModels() = this.map(ThemeShort::toUI)

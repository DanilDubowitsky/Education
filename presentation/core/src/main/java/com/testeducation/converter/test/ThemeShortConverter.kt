package com.testeducation.converter.test

import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.screen.theme.ThemeShortUI

fun ThemeShort.toUI() = ThemeShortUI(
    id,
    title
)

fun List<ThemeShort>.toUIModels() = this.map(ThemeShort::toUI)

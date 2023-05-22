package com.example.converter.test

import com.example.domain.model.test.ThemeShort
import com.example.logic.model.theme.ThemeShortUI

fun ThemeShort.toUI() = ThemeShortUI(
    id,
    title
)

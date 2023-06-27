package com.testeducation.screen.tests.filters

import com.testeducation.domain.model.theme.ThemeShort

data class TestsFiltersModelState(
    val themes: List<ThemeShort> = emptyList(),
    val selectedTheme: String? = null
)

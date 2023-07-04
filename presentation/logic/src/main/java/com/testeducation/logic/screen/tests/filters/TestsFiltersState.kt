package com.testeducation.logic.screen.tests.filters

import com.testeducation.logic.model.theme.ThemeShortUI

data class TestsFiltersState(
    val themes: List<ThemeShortUI>,
    val isTimeLimited: Boolean,
    val filterResultCount: Int,
    val isLoading: Boolean,
    val selectedThemeIndex: Int?
)

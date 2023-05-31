package com.testeducation.logic.screen.tests.creation

import com.testeducation.logic.model.theme.ThemeShortUI

data class TestCreationState(
    val themes: List<ThemeShortUI>,
    val isLoading: Boolean
)
package com.example.logic.screen.tests.creation

import com.example.logic.model.theme.ThemeShortUI

data class TestCreationState(
    val themes: List<ThemeShortUI>,
    val isLoading: Boolean
)
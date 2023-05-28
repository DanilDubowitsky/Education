package com.example.logic.screen.tests

import com.example.logic.model.test.TestShortUI
import com.example.logic.model.theme.ThemeShortUI

data class TestsState(
    val tests: List<TestShortUI>,
    val themes: List<ThemeShortUI>,
    val isLoading: Boolean,
    val userName: String
)

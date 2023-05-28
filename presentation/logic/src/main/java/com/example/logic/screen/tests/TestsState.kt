package com.example.logic.screen.tests

import com.example.logic.model.test.TestShortUI
import com.example.logic.model.theme.ThemeShortUI

data class TestsState(
    val tests: List<TestShortUI>,
    val themes: List<ThemeShortUI>,
    val userName: String,
    val isProfileLoading: Boolean,
    val isThemesLoading: Boolean,
    val isTestsLoading: Boolean,
)

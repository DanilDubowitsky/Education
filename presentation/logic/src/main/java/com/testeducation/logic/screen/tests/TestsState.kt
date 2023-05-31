package com.testeducation.logic.screen.tests

import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI

data class TestsState(
    val tests: List<TestShortUI>,
    val themes: List<ThemeShortUI>,
    val userName: String,
    val isProfileLoading: Boolean,
    val isThemesLoading: Boolean,
    val isTestsLoading: Boolean,
)
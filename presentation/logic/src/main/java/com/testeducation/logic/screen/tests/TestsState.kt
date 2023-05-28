package com.testeducation.logic.screen.tests

import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.theme.ThemeShortUI

data class TestsState(
    val tests: List<TestShortUI>,
    val themes: List<ThemeShortUI>,
    val isLoading: Boolean,
    val userName: String
)

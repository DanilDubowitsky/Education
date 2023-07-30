package com.testeducation.screen.tests.liked

import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort

data class LikedTestsModelState(
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val selectedThemeId: String? = null,
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val themesLoadingState: ThemeLoadingState = ThemeLoadingState.LOADING
) {

    enum class TestsLoadingState {
        LOADING,
        IDLE
    }

    enum class ThemeLoadingState {
        LOADING,
        IDLE
    }
}

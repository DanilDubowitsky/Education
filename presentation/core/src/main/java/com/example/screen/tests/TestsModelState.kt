package com.example.screen.tests

import com.example.domain.model.test.TestShort
import com.example.domain.model.theme.ThemeShort
import com.example.domain.model.user.User

data class TestsModelState(
    val user: User? = null,
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val profileLoadingState: ProfileLoadingState = ProfileLoadingState.LOADING,
    val themesLoadingState: ThemesLoadingState = ThemesLoadingState.LOADING,
    val selectedThemeId: String? = null
) {

    enum class ProfileLoadingState {
        LOADING,
        IDLE
    }

    enum class ThemesLoadingState {
        LOADING,
        IDLE
    }

    enum class TestsLoadingState {
        LOADING,
        IDLE
    }
}

package com.testeducation.screen.tests.list

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.model.user.User

data class TestsModelState(
    val user: User? = null,
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val profileLoadingState: ProfileLoadingState = ProfileLoadingState.LOADING,
    val themesLoadingState: ThemesLoadingState = ThemesLoadingState.LOADING,
    val selectedThemeId: String? = null,
    val selectedOrderField: TestOrderField = TestOrderField.CREATION
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

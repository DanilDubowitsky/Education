package com.testeducation.screen.tests.list

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.model.user.User
import com.testeducation.screen.tests.filters.TestsFiltersModelState
import com.testeducation.screen.tests.list.TestsViewModel.Companion.DEFAULT_HAS_LIMIT
import com.testeducation.screen.tests.list.TestsViewModel.Companion.DEFAULT_QUESTIONS_MAX
import com.testeducation.screen.tests.list.TestsViewModel.Companion.DEFAULT_QUESTIONS_MIN
import com.testeducation.screen.tests.list.TestsViewModel.Companion.DEFAULT_TIME_MAX
import com.testeducation.screen.tests.list.TestsViewModel.Companion.DEFAULT_TIME_MIN

data class TestsModelState(
    val user: User? = null,
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val profileLoadingState: ProfileLoadingState = ProfileLoadingState.LOADING,
    val themesLoadingState: ThemesLoadingState = ThemesLoadingState.LOADING,
    val selectedThemeId: String? = null,
    val selectedOrderField: TestOrderField = TestOrderField.CREATION,
    val isTimeLimited: Boolean = DEFAULT_HAS_LIMIT,
    val timeLimitFrom: String = DEFAULT_TIME_MIN,
    val timeLimitTo: String = DEFAULT_TIME_MAX,
    val questionsLimitFrom: String = DEFAULT_QUESTIONS_MIN,
    val questionsLimitTo: String = DEFAULT_QUESTIONS_MAX,
    val pageIndex: Int = 0,
    val totalTestsCount: Int = 0
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
        IDLE,
        NEXT_PAGE
    }
}

package com.testeducation.screen.tests.list

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.model.user.User
import com.testeducation.screen.tests.base.TestsDefaults

data class TestsModelState(
    val user: User? = null,
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val profileLoadingState: ProfileLoadingState = ProfileLoadingState.LOADING,
    val themesLoadingState: ThemesLoadingState = ThemesLoadingState.LOADING,
    val selectedThemeId: String? = null,
    val selectedOrderField: TestOrderField = TestOrderField.CREATION,
    val isTimeLimited: Boolean = TestsDefaults.DEFAULT_HAS_LIMIT,
    val timeLimitFrom: String = TestsDefaults.DEFAULT_TIME_MIN,
    val timeLimitTo: String = TestsDefaults.DEFAULT_TIME_MAX,
    val questionsLimitFrom: String = TestsDefaults.DEFAULT_QUESTIONS_MIN,
    val questionsLimitTo: String = TestsDefaults.DEFAULT_QUESTIONS_MAX,
    val totalTestsCount: Int = 0,
    val selectedDirection: OrderDirection = OrderDirection.DESCENDING
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

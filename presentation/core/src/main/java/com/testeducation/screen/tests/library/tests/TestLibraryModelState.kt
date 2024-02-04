package com.testeducation.screen.tests.library.tests

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.screen.tests.base.TestsDefaults

data class TestLibraryModelState(
    val tests: List<TestShort> = emptyList(),
    val themes: List<ThemeShort> = emptyList(),
    val selectedThemeId: String? = null,
    val testsLoadingState: TestsLoadingState = TestsLoadingState.LOADING,
    val themesLoadingState: ThemeLoadingState = ThemeLoadingState.LOADING,
    val selectedOrderField: TestOrderField = TestOrderField.CREATION,
    val isTimeLimited: Boolean = TestsDefaults.DEFAULT_HAS_LIMIT,
    val timeLimitFrom: String = TestsDefaults.DEFAULT_TIME_MIN,
    val timeLimitTo: String = TestsDefaults.DEFAULT_TIME_MAX,
    val questionsLimitFrom: String = TestsDefaults.DEFAULT_QUESTIONS_MIN,
    val questionsLimitTo: String = TestsDefaults.DEFAULT_QUESTIONS_MAX,
    val totalTestsCount: Int = 0,
    val selectedDirection: OrderDirection = OrderDirection.DESCENDING
) {

    enum class TestsLoadingState {
        NEXT_PAGE,
        LOADING,
        IDLE
    }

    enum class ThemeLoadingState {
        LOADING,
        IDLE
    }
}

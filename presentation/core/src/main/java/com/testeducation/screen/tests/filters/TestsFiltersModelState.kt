package com.testeducation.screen.tests.filters

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort

data class TestsFiltersModelState(
    val themes: List<ThemeShort> = emptyList(),
    val selectedTheme: String? = null,
    val isTimeLimited: Boolean = false,
    val timeLimitFrom: Int = 0,
    val timeLimitTo: Int = 0,
    val questionsLimitFrom: Int = 0,
    val questionsLimitTo: Int = 0,
    val testOrderField: TestOrderField? = null,
    val filterResultCount: Int? = null,
    val result: List<TestShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.IDLE
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}

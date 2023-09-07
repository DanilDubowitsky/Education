package com.testeducation.screen.tests.filters

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.TestGetTypeUI

data class TestsFiltersModelState(
    val themes: List<ThemeShort> = emptyList(),
    val selectedTheme: String? = null,
    val isTimeLimited: Boolean = false,
    val timeLimitFrom: String = "",
    val timeLimitTo: String = "",
    val questionsLimitFrom: String = "",
    val questionsLimitTo: String = "",
    val testOrderField: TestOrderField? = null,
    val filterResultCount: Int? = null,
    val result: List<TestShort> = emptyList(),
    val loadingState: LoadingState = LoadingState.IDLE,
    val testGetTypeUI: TestGetTypeUI
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}

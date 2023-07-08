package com.testeducation.screen.tests.filters

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.utils.indexOfByConditionOrNull

class TestsFiltersReducer : IReducer<TestsFiltersModelState, TestsFiltersState> {

    override fun reduce(modelState: TestsFiltersModelState): TestsFiltersState {
        return modelState.run {
            val selectedThemeIndex = themes.indexOfByConditionOrNull(
                ThemeShort::id,
                selectedTheme
            )
            TestsFiltersState(
                themes = themes.toUIModels(modelState.selectedTheme),
                isTimeLimited = isTimeLimited,
                filterResultCount = filterResultCount ?: result.size,
                isLoading = loadingState == TestsFiltersModelState.LoadingState.LOADING,
                selectedThemeIndex
            )
        }
    }

}

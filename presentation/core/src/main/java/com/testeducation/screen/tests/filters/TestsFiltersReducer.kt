package com.testeducation.screen.tests.filters

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.filters.TestsFiltersState

class TestsFiltersReducer : IReducer<TestsFiltersModelState, TestsFiltersState> {

    override fun reduce(modelState: TestsFiltersModelState): TestsFiltersState {
        return TestsFiltersState(
            themes = modelState.themes.toUIModels(modelState.selectedTheme)
        )
    }

}

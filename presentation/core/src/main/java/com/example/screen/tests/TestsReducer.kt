package com.example.screen.tests

import com.example.converter.test.toUIModels
import com.example.core.IReducer
import com.example.logic.screen.tests.TestsState

class TestsReducer : IReducer<TestsModelState, TestsState> {

    override fun reduce(modelState: TestsModelState): TestsState {
        return modelState.run {
            TestsState(
                tests = tests.toUIModels(),
                isTestsLoading = testsLoadingState
                        == TestsModelState.TestsLoadingState.LOADING,
                userName = user?.userName.orEmpty(),
                themes = themes.toUIModels(modelState.selectedThemeId),
                isProfileLoading = profileLoadingState
                        == TestsModelState.ProfileLoadingState.LOADING,
                isThemesLoading = themesLoadingState
                        == TestsModelState.ThemesLoadingState.LOADING
            )
        }
    }

}

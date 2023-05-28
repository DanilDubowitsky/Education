package com.testeducation.screen.tests

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.TestsState

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

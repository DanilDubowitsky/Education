package com.testeducation.screen.tests.list

import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.tests.list.TestsState

class TestsReducer : IReducer<TestsModelState, TestsState> {

    override fun reduce(modelState: TestsModelState): TestsState {
        return modelState.run {
            var testsUI = tests.toUIModels()
            if (testsLoadingState == TestsModelState.TestsLoadingState.NEXT_PAGE) {
               testsUI = testsUI + TestShortUI.Loader
            }

            TestsState(
                tests = testsUI,
                isTestsLoading = testsLoadingState
                        == TestsModelState.TestsLoadingState.LOADING,
                userName = user?.userName.orEmpty(),
                themes = themes.toUIModels(modelState.selectedThemeId),
                isProfileLoading = profileLoadingState
                        == TestsModelState.ProfileLoadingState.LOADING,
                isThemesLoading = themesLoadingState
                        == TestsModelState.ThemesLoadingState.LOADING,
                selectedSortField = selectedOrderField.toUIModel()
            )
        }
    }

}

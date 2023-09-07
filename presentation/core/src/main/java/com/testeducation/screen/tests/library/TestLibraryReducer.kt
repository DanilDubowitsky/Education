package com.testeducation.screen.tests.library

import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.library.TestLibraryState

class TestLibraryReducer : IReducer<TestLibraryModelState, TestLibraryState> {

    override fun reduce(modelState: TestLibraryModelState): TestLibraryState {
        return modelState.run {
            TestLibraryState(
                tests = tests.toUIModels(),
                themes = themes.toUIModels(selectedThemeId),
                isTestsLoading = testsLoadingState == TestLibraryModelState.TestsLoadingState.LOADING,
                isThemesLoading =  themesLoadingState == TestLibraryModelState.ThemeLoadingState.LOADING,
                selectedSortField = selectedOrderField.toUIModel()
            )
        }
    }

}

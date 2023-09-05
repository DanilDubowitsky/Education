package com.testeducation.screen.tests.liked

import com.testeducation.converter.test.toUIModel
import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.screen.tests.liked.LikedTestsState

class LikedTestsReducer : IReducer<LikedTestsModelState, LikedTestsState> {

    override fun reduce(modelState: LikedTestsModelState): LikedTestsState {
        var tests = modelState.tests.toUIModels()
        if (modelState.testsLoadingState == LikedTestsModelState.TestsLoadingState.NEXT_PAGE) {
            tests = tests + TestShortUI.Loader
        }
        val themes = modelState.themes.toUIModels(modelState.selectedThemeId)
        return LikedTestsState(
            tests = tests,
            themes = themes,
            isThemesLoading = modelState.themesLoadingState ==
                    LikedTestsModelState.ThemeLoadingState.LOADING,
            isTestsLoading = modelState.testsLoadingState ==
                    LikedTestsModelState.TestsLoadingState.LOADING,
            selectedSortField = modelState.selectedOrderField.toUIModel()
        )
    }

}

package com.testeducation.screen.tests.liked

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.liked.LikedTestsState

class LikedTestsReducer : IReducer<LikedTestsModelState, LikedTestsState> {

    override fun reduce(modelState: LikedTestsModelState): LikedTestsState {
        val tests = modelState.tests.toUIModels()
        val themes = modelState.themes.toUIModels(modelState.selectedThemeId)
        return LikedTestsState(
            tests = tests,
            themes = themes,
            isThemesLoading = modelState.themesLoadingState ==
                    LikedTestsModelState.ThemeLoadingState.LOADING,
            isTestsLoading = modelState.testsLoadingState ==
                    LikedTestsModelState.TestsLoadingState.LOADING
        )
    }

}

package com.testeducation.screen.tests.liked

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetLikedTests
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.logic.screen.tests.liked.LikedTestsSideEffect
import com.testeducation.logic.screen.tests.liked.LikedTestsState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class LikedTestsViewModel(
    private val router: NavigationRouter,
    private val testShortHelper: ITestHelper,
    private val getThemes: GetThemes,
    private val getLikedTests: GetLikedTests,
    reducer: IReducer<LikedTestsModelState, LikedTestsState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<LikedTestsModelState, LikedTestsState, LikedTestsSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: LikedTestsModelState = LikedTestsModelState()

    init {
        loadTests()
        loadThemes()
    }

    fun toggleTestLike(position: Int) = intent {
        val modelState = getModelState()
        val newTests = testShortHelper.toggleTestLike(
            position,
            modelState.tests,
            removeFromList = true
        )
        updateModelState {
            copy(tests = newTests)
        }
    }

    fun onThemeChanged(themeId: String) = intent {
        updateModelState {
            copy(selectedThemeId = themeId)
        }
    }

    private fun handleThemes(themes: List<ThemeShort>) = intent {
        updateModelState {
            copy(themes = themes, themesLoadingState = LikedTestsModelState.ThemeLoadingState.IDLE)
        }
    }

    private fun loadThemes() = intent {
        getThemes().collect(::handleThemes)
    }

    private fun loadTests() = intent {
        val tests = getLikedTests()
        updateModelState {
            copy(
                tests = tests,
                testsLoadingState = LikedTestsModelState.TestsLoadingState.IDLE
            )
        }
    }

}
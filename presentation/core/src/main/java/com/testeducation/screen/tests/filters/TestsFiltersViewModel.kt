package com.testeducation.screen.tests.filters

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.filters.TestsFiltersSideEffect
import com.testeducation.logic.screen.tests.filters.TestsFiltersState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class TestsFiltersViewModel(
    private val router: NavigationRouter,
    private val getThemes: GetThemes,
    reducer: IReducer<TestsFiltersModelState, TestsFiltersState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<TestsFiltersModelState, TestsFiltersState, TestsFiltersSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestsFiltersModelState = TestsFiltersModelState()

    init {
        loadThemes()
    }

    fun exit() {
        router.exit()
    }

    fun selectTheme(id: String) = intent {
        updateModelState {
            copy(selectedTheme = id)
        }
    }

    private fun loadThemes() = intent {
        val themes = getThemes()
        updateModelState {
            copy(themes = themes)
        }
    }
}
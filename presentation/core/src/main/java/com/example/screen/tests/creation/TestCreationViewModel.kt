package com.example.screen.tests.creation

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.theme.GetThemes
import com.example.helper.error.IExceptionHandler
import com.example.logic.screen.tests.TestsSideEffect
import com.example.logic.screen.tests.TestsState
import com.example.logic.screen.tests.creation.TestCreationSideEffect
import com.example.logic.screen.tests.creation.TestCreationState
import com.example.screen.tests.TestsModelState
import kotlinx.coroutines.Dispatchers
import org.orbitmvi.orbit.syntax.simple.intent

class TestCreationViewModel(
    reducer: IReducer<TestCreationModelState, TestCreationState>,
    private val getThemes: GetThemes,
    errorHandler: IExceptionHandler
) :
    BaseViewModel<TestCreationModelState, TestCreationState, TestCreationSideEffect>(
        reducer,
        errorHandler
    ) {
    override val initialModelState: TestCreationModelState = TestCreationModelState()

    init {
        updateLoadingState(TestCreationModelState.LoadingState.LOADING)
        loadingTheme()
    }

    private fun loadingTheme() = intent {
        launchJob {
            val themes = with(Dispatchers.IO) { getThemes() }
            updateModelState {
                copy(
                    themes = themes,
                    loadingState = TestCreationModelState.LoadingState.IDLE
                )
            }
        }
    }

    private fun updateLoadingState(loadingState: TestCreationModelState.LoadingState) = intent {
        updateModelState {
            copy(
                loadingState = loadingState
            )
        }
    }
}
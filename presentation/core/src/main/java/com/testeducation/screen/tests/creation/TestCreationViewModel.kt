package com.testeducation.screen.tests.creation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
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
package com.testeducation.screen.tests.creation

import androidx.lifecycle.viewModelScope
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.TestCreationState
import com.testeducation.screen.tests.creation.TestCreationModelState.StepState.Companion.isFirst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun changeStateStep() {
        viewModelScope.launch {
            val currentStepState = getModelState().stepState
            if (currentStepState.isFirst()) {
                TestCreationModelState.StepState.SECOND
            } else {
                TestCreationModelState.StepState.FIRST
            }.also { stepStateNew ->
                updateStateStep(stepStateNew)
            }
        }
    }

    private fun updateStateStep(stateStepState: TestCreationModelState.StepState) = intent {
        updateModelState {
            copy(
                stepState = stateStepState
            )
        }
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
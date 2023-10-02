package com.testeducation.screen.tests.preview

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.preview.TestPreviewSideEffect
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.navigation.core.NavigationRouter
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent

class TestPreviewViewModel(
    reducer: IReducer<TestPreviewModelState, TestPreviewState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
) : BaseViewModel<TestPreviewModelState,
        TestPreviewState, TestPreviewSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: TestPreviewModelState = TestPreviewModelState()

    init {
        loadData()
    }

    private fun loadData() = intent {
        delay(2000)
        updateModelState {
            copy(loadingState = TestPreviewModelState.LoadingState.IDLE)
        }
    }

}

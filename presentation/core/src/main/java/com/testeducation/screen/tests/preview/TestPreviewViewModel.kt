package com.testeducation.screen.tests.preview

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.preview.TestPreviewSideEffect
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestPreviewViewModel(
    reducer: IReducer<TestPreviewModelState, TestPreviewState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTest: GetTest
) : BaseViewModel<TestPreviewModelState,
        TestPreviewState, TestPreviewSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: TestPreviewModelState = TestPreviewModelState()

    init {
        loadData()
    }

    fun changeQuestionsVisibility() = intent {
        updateModelState {
            copy(isQuestionsShown = !isQuestionsShown)
        }
    }

    private fun loadData() = intent {
        val test = getTest(testId)

        updateModelState {
            copy(
                loadingState = TestPreviewModelState.LoadingState.IDLE,
                test = test
            )
        }
    }

}

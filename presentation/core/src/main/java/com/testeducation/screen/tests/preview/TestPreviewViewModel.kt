package com.testeducation.screen.tests.preview

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.ToggleTestLike
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
    private val getTest: GetTest,
    private val likeTest: ToggleTestLike
) : BaseViewModel<TestPreviewModelState,
        TestPreviewState, TestPreviewSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: TestPreviewModelState = TestPreviewModelState()

    init {
        loadData()
    }

    fun toggleDescriptionExpand() = intent {
        updateModelState {
            copy(isDescriptionExpanded = !isDescriptionExpanded)
        }
    }

    fun toggleFavorite() = intent {
        val modelState = getModelState()
        val testId = modelState.test?.id ?: return@intent
        val isLiked = modelState.test.liked
        updateModelState {
            copy(test = test?.copy(liked = !isLiked))
        }
        likeTest(testId, !isLiked)
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

package com.testeducation.screen.questions

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.questions.QuestionsPreviewSideEffect
import com.testeducation.logic.screen.questions.QuestionsPreviewState
import org.orbitmvi.orbit.syntax.simple.intent

class QuestionsPreviewViewModel(
    reducer: IReducer<QuestionsPreviewModelState, QuestionsPreviewState>,
    exceptionHandler: IExceptionHandler,
    private val testId: String,
    private val getQuestions: GetQuestions
) : BaseViewModel<QuestionsPreviewModelState, QuestionsPreviewState, QuestionsPreviewSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: QuestionsPreviewModelState = QuestionsPreviewModelState()

    init {
        loadData()
    }

    private fun loadData() = intent {
        val questions = getQuestions(testId)
        updateModelState {
            copy(
                questions = questions,
                loadingState = QuestionsPreviewModelState.LoadingState.IDLE
            )
        }
    }
}
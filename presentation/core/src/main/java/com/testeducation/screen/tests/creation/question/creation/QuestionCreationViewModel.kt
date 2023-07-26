package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import org.orbitmvi.orbit.syntax.simple.intent

class QuestionCreationViewModel (
    reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<QuestionCreationModelState, QuestionCreationState, QuestionCreationSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: QuestionCreationModelState = QuestionCreationModelState()

    init {
        initAnswerDefault()
    }

    private fun initAnswerDefault() = intent {
        updateModelState {
            copy(
                answerItem = listOf(
                    AnswerItem.DefaultAnswer(
                        id = initialModelState.answerItem.size + 1,
                    ),
                    AnswerItem.FooterPlusAdd(
                        initialModelState.answerItem.size + 2
                    )
                )
            )
        }
    }

}
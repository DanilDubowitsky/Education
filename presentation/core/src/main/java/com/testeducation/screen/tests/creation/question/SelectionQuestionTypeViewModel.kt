package com.testeducation.screen.tests.creation.question

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.QuestionTypeItem
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class SelectionQuestionTypeViewModel(
    reducer: IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState>,
    errorHandler: IExceptionHandler,
) : BaseViewModel<SelectionQuestionTypeModelState, SelectionQuestionTypeState, TestCreationSideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: SelectionQuestionTypeModelState =
        SelectionQuestionTypeModelState()

    init {
        initData()
    }


    fun submit(questionTypeUi: QuestionTypeUi) {

    }

    private fun initData() = intent {
        updateModelState {
            copy(
                questionTypeItems = listOf(
                    QuestionTypeItem(
                        questionType = QuestionType.MATCH
                    ),
                    QuestionTypeItem(
                        questionType = QuestionType.ACCORD
                    ),
                    QuestionTypeItem(
                        questionType = QuestionType.WRITE_ANSWER
                    )
                )
            )
        }
    }

}
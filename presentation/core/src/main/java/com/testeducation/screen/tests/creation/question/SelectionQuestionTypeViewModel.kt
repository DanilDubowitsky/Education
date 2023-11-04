package com.testeducation.screen.tests.creation.question

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.QuestionTypeItem
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.creation.TestCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class SelectionQuestionTypeViewModel(
    reducer: IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState>,
    errorHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val idTest: String
) : BaseViewModel<SelectionQuestionTypeModelState, SelectionQuestionTypeState, TestCreationSideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: SelectionQuestionTypeModelState =
        SelectionQuestionTypeModelState()

    init {
        initData()
    }


    fun submit(questionTypeUiItem: QuestionTypeUiItem) {
        if (idTest.isNotEmpty()) {
            router.exit()
            router.navigateTo(
                NavigationScreen.Questions.QuestionEditor(
                    questionTypeUiItem,
                    idTest
                ),
                false
            )
        } else {
            router.sendResult(NavigationScreen.Questions.OnSelectionQuestionTypeChanged, questionTypeUiItem)
            router.exit()
        }
    }

    private fun initData() = intent {
        updateModelState {
            copy(
                questionTypeItems = listOf(
                    QuestionTypeItem(
                        questionType = QuestionType.CHOICE
                    ),
                    QuestionTypeItem(
                        questionType = QuestionType.REORDER
                    ),
                    QuestionTypeItem(
                        questionType = QuestionType.MATCH
                    ),
                    QuestionTypeItem(
                        questionType = QuestionType.TEXT
                    )
                )
            )
        }
    }

}
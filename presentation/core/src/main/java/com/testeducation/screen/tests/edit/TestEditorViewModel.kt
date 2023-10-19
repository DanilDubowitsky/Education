package com.testeducation.screen.tests.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.edit.TestEditorSideEffect
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen

class TestEditorViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestEditorModelState, TestEditorState>,
    private val resourceHelper: IResourceHelper,
    private val testId: String,
    private val getTest: GetTest,
    private val questionResourceHelper: IQuestionResourceHelper,
    private val router: NavigationRouter,
) : BaseViewModel<TestEditorModelState, TestEditorState, TestEditorSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestEditorModelState = TestEditorModelState()

    init {
        getTestDetails(testId = testId)
    }

    fun openCreateQuestion() {
        router.navigateTo(
            NavigationScreen.Questions.QuestionEditor(
                QuestionTypeUiItem(QuestionTypeUi.DEFAULT),
                testId
            )
        )
    }

    private fun getTestDetails(testId: String) = singleIntent(getTest.javaClass.name) {
        val details = getTest.invoke(id = testId)
        val questionItems = questionResourceHelper.getQuestionItemPrepared(details.questions)
        val questionDetails : MutableList<QuestionDetails> = mutableListOf()
        questionDetails.addAll(questionItems.prepareQuestionDetailsItems())
        questionDetails.add(QuestionDetails.FooterAdd())
        updateModelState {
            copy(
                test = details.copy(
                    questions = questionItems
                ),
                questionDetails = questionDetails
            )
        }
    }

    private fun List<InputQuestion>.prepareQuestionDetailsItems() = map {
        QuestionDetails.QuestionItemDetails(
            id = it.id,
            question = it
        )
    }
}
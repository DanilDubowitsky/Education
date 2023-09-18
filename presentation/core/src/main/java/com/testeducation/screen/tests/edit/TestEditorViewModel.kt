package com.testeducation.screen.tests.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestDetails
import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.question.QuestionItem
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.edit.TestEditorSideEffect
import com.testeducation.logic.screen.tests.edit.TestEditorState

class TestEditorViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestEditorModelState, TestEditorState>,
    private val resourceHelper: IResourceHelper,
    private val testId: String,
    private val getTestDetails: GetTestDetails,
    private val questionResourceHelper: IQuestionResourceHelper
) : BaseViewModel<TestEditorModelState, TestEditorState, TestEditorSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestEditorModelState = TestEditorModelState()

    init {
        getTestDetails(testId = testId)
    }

    private fun getTestDetails(testId: String) = singleIntent(getTestDetails.javaClass.name) {
        val details = getTestDetails.invoke(id = testId)
        val questionItems = questionResourceHelper.getQuestionItemPrepared(details.questions)
        val questionDetails : MutableList<QuestionDetails> = mutableListOf()
        questionDetails.addAll(questionItems.prepareQuestionDetailsItems())
        questionDetails.add(QuestionDetails.FooterAdd())
        updateModelState {
            copy(
                testDetails = details.copy(
                    questions = questionItems
                ),
                questionDetails = questionDetails
            )
        }
    }

    private fun List<QuestionItem>.prepareQuestionDetailsItems() = map {
        QuestionDetails.QuestionItemDetails(
            id = it.id,
            question = it
        )
    }
}
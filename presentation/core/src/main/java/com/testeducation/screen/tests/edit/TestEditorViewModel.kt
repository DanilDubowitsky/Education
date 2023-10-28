package com.testeducation.screen.tests.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.model.question.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.question.input.InputAnswer
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
import org.orbitmvi.orbit.syntax.simple.intent

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
        initData()
    }

    fun openCreateQuestion() {
        router.navigateTo(
            NavigationScreen.Questions.QuestionEditor(
                QuestionTypeUiItem(QuestionTypeUi.DEFAULT),
                testId
            )
        )
    }

    fun openTestSettings() = intent {
        val modelState = getModelState()
        router.setResultListener(NavigationScreen.Tests.Settings.OnTestSettingsResult) { testSettings ->
            initData()
        }
        modelState.test?.let { testItem ->
            router.navigateTo(
                NavigationScreen.Tests.Settings(
                    testId = testId,
                    titleTest = testItem.title,
                    colorTest = testItem.style.color,
                    imageTest = testItem.style.background,
                    idTheme = testItem.theme.id,
                    themeName = testItem.theme.title
                )
            )
        }
    }

    private fun initData() = getTestDetails(testId = testId)

    private fun getTestDetails(testId: String) = singleIntent(getTest.javaClass.name) {
        val details = getTest.invoke(id = testId)
        val questionItems = questionResourceHelper.getQuestionItemPrepared(details.questions.convertToQuestionDomain())
        val questionDetails: MutableList<QuestionDetails> = mutableListOf()
        questionDetails.addAll(questionItems.prepareQuestionDetailsItems())
        questionDetails.add(QuestionDetails.FooterAdd())
        updateModelState {
            copy(
                test = details,
                questionDetails = questionDetails
            )
        }
    }

    private fun List<Question>.convertToQuestionDomain() = map { itemQuestion ->
        InputQuestion(
            id = itemQuestion.id,
            title = itemQuestion.title,
            numberQuestion = itemQuestion.numberQuestion,
            time = itemQuestion.time,
            icon = 0,
            type = itemQuestion.type,
            answers = itemQuestion.answers.convertToDomain()
        )
    }

    private fun List<Answer>.convertToDomain() = map { itemAnswer ->
        when (itemAnswer) {
            is Answer.ChoiceAnswer -> {
                InputAnswer.DefaultAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    isTrue = itemAnswer.isTrue
                )
            }

            is Answer.MatchAnswer -> {
                InputAnswer.MatchAnswer(
                    id = itemAnswer.id,
                    firstAnswer = itemAnswer.title,
                    secondAnswer = itemAnswer.matchedCorrectText
                )
            }

            is Answer.OrderAnswer -> {
                InputAnswer.OrderAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    order = itemAnswer.order
                )
            }

            is Answer.TextAnswer -> {
                InputAnswer.TextAnswer(
                    id = itemAnswer.id,
                    text = itemAnswer.questionId
                )
            }
        }

    }

    private fun List<InputQuestion>.prepareQuestionDetailsItems() = map {
        QuestionDetails.QuestionItemDetails(
            id = it.id,
            question = it
        )
    }
}
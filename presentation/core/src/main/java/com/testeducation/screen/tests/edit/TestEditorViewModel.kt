package com.testeducation.screen.tests.edit

import com.testeducation.converter.test.answer.toInputAnswers
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.DeleteQuestion
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.test.ChangeStatusTest
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.domain.model.test.Test
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.edit.TestEditorSideEffect
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent

class TestEditorViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestEditorModelState, TestEditorState>,
    private val resourceHelper: IResourceHelper,
    private val testId: String,
    private val getTest: GetTest,
    private val deleteQuestion: DeleteQuestion,
    private val questionResourceHelper: IQuestionResourceHelper,
    private val router: NavigationRouter,
    private val changeStatusTest: ChangeStatusTest,
    private val getQuestions: GetQuestions
) : BaseViewModel<TestEditorModelState, TestEditorState, TestEditorSideEffect>(
    reducer,
    exceptionHandler
) {

    companion object {
        private const val MIN_QUESTION = 2
    }

    override val initialModelState: TestEditorModelState = TestEditorModelState()

    init {
        initData()
    }

    override fun handleThrowable(throwable: Throwable) {
        super.handleThrowable(throwable)
        intent {
            updateModelState {
                copy(
                    loadingPublish = true
                )
            }
        }
    }

    fun onExit() {
        router.navigateTo(NavigationScreen.Main.Home, false)
    }

    fun deleteQuestion(questionId: String) = intent {
        val modelState = getModelState()
        modelState.test?.id?.let { idNotNull ->
            deleteQuestion.invoke(testId = idNotNull, questionId = questionId)
            getTestDetails(idNotNull)
        }
    }

    fun openEditQuestion(questionId: String) {
        intent {
            val modelState = getModelState()
            modelState.test?.id?.let { idNotNull ->
                router.navigateTo(
                    NavigationScreen.Questions.QuestionEditor(
                        questionTypeUiItem = QuestionTypeUiItem(QuestionTypeUi.DEFAULT),
                        testId = testId,
                        orderQuestion = modelState.questionDetails.size,
                        questionId = questionId
                    )
                )
            }
        }
    }

    fun openCreateQuestion() {
        intent {
            val modelState = getModelState()
            router.navigateTo(
                NavigationScreen.Questions.QuestionEditor(
                    QuestionTypeUiItem(QuestionTypeUi.DEFAULT),
                    testId,
                    modelState.questionDetails.size
                )
            )
        }
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

    fun publish() {
        intent {
            if (validate()) {
                updateModelState {
                    copy(
                        loadingPublish = true
                    )
                }
                changeStatusTest.invoke(
                    testId,
                    Test.Status.PUBLISHED
                )
                router.navigateTo(NavigationScreen.Main.Home, false)
            }
        }
    }

    fun draft() {
        intent {
            if (validate()) {
                updateModelState {
                    copy(
                        loadingPublish = true
                    )
                }
                changeStatusTest.invoke(
                    testId,
                    Test.Status.DRAFT
                )
                router.navigateTo(NavigationScreen.Main.Home, false)
            }
        }
    }

    private suspend fun validate() : Boolean {
        val modelState = getModelState()
        if (modelState.questionDetails.size - 1 < MIN_QUESTION) {
            val screen = NavigationScreen.Common.Information(
                titleText = StringResource.Validate.TestEditErrorTitle.getString(resourceHelper),
                description = StringResource.Validate.MaxQuestionValue(MIN_QUESTION).getString(resourceHelper),
                btnText = StringResource.Common.CommonNext.getString(resourceHelper)
            )
            router.navigateTo(screen)
            return false
        }
        return true
    }

    private fun initData() = getTestDetails(testId = testId)

    private fun getTestDetails(testId: String) = singleIntent(getTest.javaClass.name) {
        val details = getTest.invoke(id = testId)
        val questions = getQuestions(testId)
        val questionItems =
            questionResourceHelper.getQuestionItemPrepared(questions.convertToQuestionDomain())
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

        var answers = when (itemQuestion) {
            is Question.Choice -> itemQuestion.answers
            is Question.Match -> itemQuestion.answers
            is Question.Order -> itemQuestion.answers
            is Question.Text -> emptyList()
        }.toInputAnswers()

        if (answers.all { it is InputAnswer.OrderAnswer }) {
            answers = (answers as List<InputAnswer.OrderAnswer>).sortCompleted()
        }

        val type = when (itemQuestion) {
            is Question.Choice -> QuestionType.CHOICE
            is Question.Match -> QuestionType.MATCH
            is Question.Order -> QuestionType.REORDER
            is Question.Text -> QuestionType.TEXT
        }

        InputQuestion(
            id = itemQuestion.id,
            title = itemQuestion.title,
            numberQuestion = itemQuestion.numberQuestion.toString(),
            time = itemQuestion.time,
            icon = 0,
            type = type,
            answers = answers
        )
    }

    private fun List<InputAnswer.OrderAnswer>.sortCompleted() = this.sortedBy {
        it.order
    }

    private fun List<InputQuestion>.prepareQuestionDetailsItems() = map {
        QuestionDetails.QuestionItemDetails(
            id = it.id,
            question = it
        )
    }
}
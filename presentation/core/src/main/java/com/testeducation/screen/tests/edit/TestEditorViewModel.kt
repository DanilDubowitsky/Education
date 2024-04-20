package com.testeducation.screen.tests.edit

import com.testeducation.converter.test.answer.toInputAnswers
import com.testeducation.converter.test.question.toUiModel
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.DeleteQuestion
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.test.ChangeStatusTest
import com.testeducation.domain.cases.test.DeleteTest
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.domain.model.question.input.InputQuestion
import com.testeducation.domain.model.test.Test
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.IQuestionResourceHelper
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.edit.TestEditorSideEffect
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getColor
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent

class TestEditorViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestEditorModelState, TestEditorState>,
    private val navigateFrom: NavigationScreen.Tests.Details.NavigateFrom,
    private val resourceHelper: IResourceHelper,
    private val testId: String,
    private val getTest: GetTest,
    private val deleteQuestion: DeleteQuestion,
    private val questionResourceHelper: IQuestionResourceHelper,
    private val router: NavigationRouter,
    private val changeStatusTest: ChangeStatusTest,
    private val getQuestions: GetQuestions,
    private val deleteTest: DeleteTest
) : BaseViewModel<TestEditorModelState, TestEditorState, TestEditorSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestEditorModelState = TestEditorModelState()

    init {
        initData()
    }

    override fun handleThrowable(throwable: Throwable) {
        super.handleThrowable(throwable)
        intent {
            updateModelState {
                copy(
                    loadingPublish = false
                )
            }
        }
    }

    fun onExit() {
        router.setResultListener(NavigationScreen.Common.ConfirmationBottom.ButtonRight) {
            exit()
        }
        intent {
            val modelState = getModelState()
            val isDraft = modelState.test?.status == Test.Status.DRAFT
            router.navigateTo(NavigationScreen.Common.ConfirmationBottom(
                title = StringResource.Test.TestEditExitTitle.getString(resourceHelper),
                description = StringResource.Test.TestEditExitDescription(isDraft).getString(resourceHelper),
                buttonLeft = NavigationScreen.Common.ConfirmationBottom.Button(
                    text = StringResource.Common.CommonCancel.getString(resourceHelper),
                    color = ColorResource.Main.Red.getColor(resourceHelper)
                ),
                buttonRight = NavigationScreen.Common.ConfirmationBottom.Button(
                    text = StringResource.Common.CommonNext.getString(resourceHelper),
                    color = ColorResource.Main.Green.getColor(resourceHelper)
                ),
            ))
        }
    }

    fun deleteText() {
        router.setResultListener(NavigationScreen.Common.ConfirmationBottom.ButtonLeft) {
            intent {
                updateModelState {
                    copy(
                        loadingPublish = true
                    )
                }
                deleteTest(testId)
                exit()
            }
        }
        router.navigateTo(NavigationScreen.Common.ConfirmationBottom(
            title = StringResource.Test.TestDeleteTitle.getString(resourceHelper),
            description = StringResource.Test.TestDeleteDescription.getString(resourceHelper),
            buttonLeft = NavigationScreen.Common.ConfirmationBottom.Button(
                text = StringResource.Common.Delete.getString(resourceHelper),
                color = ColorResource.Main.Red.getColor(resourceHelper)
            ),
            buttonRight = NavigationScreen.Common.ConfirmationBottom.Button(
                text = StringResource.Common.CommonCancel.getString(resourceHelper),
                color = ColorResource.Main.Green.getColor(resourceHelper)
            )
        ))
    }

    fun deleteQuestion(questionId: String) = intent {
        router.setResultListener(NavigationScreen.Common.Confirmation.OnConfirm) {
           intent {
               val modelState = getModelState()
               modelState.test?.id?.let { idNotNull ->
                   deleteQuestion.invoke(testId = idNotNull, questionId = questionId)
                   updateModelState {
                       copy(
                           questionDetails = modelState.questionDetails.toMutableList().mapNotNull {
                               if (questionId == it.id) {
                                   null
                               } else it
                           }
                       )
                   }
                   getTestDetails(idNotNull)
               }
           }

        }
        router.navigateTo(
            NavigationScreen.Common.Confirmation(
                StringResource.Test.TestQuestionDeleteDescription.getString(resourceHelper),
                StringResource.Test.TestQuestionDeleteTitle.getString(resourceHelper),
                StringResource.Common.Delete.getString(resourceHelper),
                StringResource.Common.CommonCancel.getString(resourceHelper)
            )
        )
    }

    fun openEditQuestion(questionId: String) {
        intent {
            val modelState = getModelState()
            modelState.test?.id?.let { idNotNull ->
                val itemQuestion = modelState.questionDetails.find { it.id == questionId }
                if (itemQuestion is QuestionDetails.QuestionItemDetails) {
                    router.navigateTo(
                        NavigationScreen.Questions.QuestionEditor(
                            questionTypeUiItem = itemQuestion.question.type.toUiModel(),
                            testId = testId,
                            orderQuestion = modelState.questionDetails.size,
                            questionId = questionId
                        )
                    )
                }
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
                    themeName = testItem.theme.title,
                    countQuestion = testItem.questionsCount
                )
            )
        }
    }

    fun saveTestProcess() = intent {
        val modelState = getModelState()
        router.setResultListener(NavigationScreen.Tests.TestPublish.OnTestPublish) { status ->
            if (status.statusPublish.isPublish()) {
                publish()
            } else draft()
        }
        modelState.test?.let { testItem ->
            router.navigateTo(NavigationScreen.Tests.TestPublish(
                isPublishTest = testItem.status == Test.Status.PUBLISHED
            ))
        }
    }

    fun publish() {
        intent {
            val modelState = getModelState()
            if (modelState.test?.status != Test.Status.PUBLISHED) {
                updateModelState {
                    copy(
                        loadingPublish = true
                    )
                }
                changeStatusTest.invoke(
                    testId,
                    Test.Status.PUBLISHED
                )
            }
            navigateFinish()
        }
    }

    fun draft() {
        intent {
            updateModelState {
                copy(
                    loadingPublish = true
                )
            }
            changeStatusTest.invoke(
                testId,
                Test.Status.DRAFT
            )
            navigateFinish()
        }
    }

    fun swipeOnRefresh() = intent {
        updateModelState {
            copy(
                isRefreshing = true
            )
        }
        initData()
    }

    private fun exit() {
        if (navigateFrom.fromCreate) {
            router.newRootChain(NavigationScreen.Main.Home)
        } else {
            router.sendResult(NavigationScreen.Tests.Details.OnTestEditorUpdated, Unit)
            router.exit()
        }
    }

    private fun navigateFinish() {
        if (navigateFrom.fromCreate) {
            router.navigateTo(NavigationScreen.Main.Home, false)
        } else {
            router.sendResult(NavigationScreen.Tests.Details.OnTestEditorUpdated, Unit)
            router.exit()
        }
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
                questionDetails = questionDetails,
                isRefreshing = false
            )
        }
    }

    private fun List<Question>.convertToQuestionDomain() = map { itemQuestion ->

        var answers = when (itemQuestion) {
            is Question.Choice -> itemQuestion.answers
            is Question.Match -> itemQuestion.answers
            is Question.Order -> itemQuestion.answers
            is Question.Text -> itemQuestion.answers
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
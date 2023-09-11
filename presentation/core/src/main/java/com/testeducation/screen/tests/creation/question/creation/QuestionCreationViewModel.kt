package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.converter.test.question.toModel
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.QuestionCreate
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getColor
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class QuestionCreationViewModel(
    reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper,
    questionTypeItem: QuestionTypeUiItem,
    private val router: NavigationRouter,
    private val questionCreate: QuestionCreate,
    private val testId: String
) : BaseViewModel<QuestionCreationModelState, QuestionCreationState, QuestionCreationSideEffect>(
    reducer,
    errorHandler
) {

    companion object {
        private const val ERROR_NUMBER_TEXT = "The number cannot be more than 2"
    }

    override val initialModelState: QuestionCreationModelState = QuestionCreationModelState(
        questionTypeItem = questionTypeItem.toModel()
    )

    init {
        initQuestionType()
    }

    override fun handleThrowable(throwable: Throwable) {
        intent {
            postSideEffect(
                QuestionCreationSideEffect.LoaderInvisible
            )
        }
        super.handleThrowable(throwable)
    }

    fun saveQuestion() = intent {
        postSideEffect(
            QuestionCreationSideEffect.LoaderVisible
        )
        val modelState = getModelState()
        questionCreate(
            testId = testId,
            type = modelState.questionTypeItem.questionType,
            questionText = modelState.questionText,
            answerItem = modelState.answerItem
        )
        router.navigateTo(NavigationScreen.Tests.Details(testId))
        postSideEffect(
            QuestionCreationSideEffect.LoaderInvisible
        )
    }

    fun updateQuestionText(textQuestion: String) = intent {
        updateModelState {
            copy(
                questionText = textQuestion
            )
        }
    }

    fun changeTypeQuestion() {
        router.setResultListener(NavigationScreen.QuestionCreation.OnSelectionQuestionTypeChanged) { questionTypeUiItem ->
            intent {
                val questionTypeItem = questionTypeUiItem.toModel()
                updateModelState {
                    copy(
                        questionTypeItem = questionTypeItem,
                        answerItem = emptyList()
                    )
                }
                initQuestionType(questionTypeItem.questionType)
            }
        }
        router.navigateTo(NavigationScreen.Main.SelectionTest())
    }

    fun addAnswer() = intent {
        val answerItems = getModelState().answerItem
        val index = answerItems.size - 1
        val answer = createAnswer(
            index, getModelState().questionTypeItem.questionType
        )
        updateModelState {
            copy(
                answerItem = answerItems.toMutableList().apply {
                    add(index, answer)
                }
            )
        }
    }

    fun deleteAnswer(selectedId: String) = intent {
        val answerItems = getModelState().answerItem.toMutableList()
        val answerForDelete = answerItems.find { item ->
            item.id == selectedId
        }
        answerForDelete?.let { answerItemNotNull ->
            updateModelState {
                copy(
                    answerItem = answerItems.apply {
                        remove(answerItemNotNull)
                    }
                )
            }
        }
    }

    fun answerTextChanger(answerId: String, text: String) = intent {
        var answerItems = getModelState().answerItem
        answerItems = answerItems.map { answerItem ->
            if (answerItem.id == answerId && answerItem is AnswerItem.DefaultAnswer) {
                answerItem.copy(
                    answerText = text
                )
            } else if (answerItem.id == answerId && answerItem is AnswerItem.TextAnswer) {
                answerItem.copy(
                    text = text
                )
            }
            else answerItem
        }
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }
    }

    fun answerMatchChanger(answerId: String, number: Int, text: String) = intent {
        if (number > 2) throw IllegalArgumentException(ERROR_NUMBER_TEXT)

        var answerItems = getModelState().answerItem
        answerItems = answerItems.map { answerItem ->
            if (answerItem.id == answerId &&
                number == AnswerItem.MatchAnswer.FIRST_ANSWER_MATCH &&
                answerItem is AnswerItem.MatchAnswer
            ) {
                answerItem.copy(
                    firstAnswer = text
                )
            } else if (
                answerItem.id == answerId &&
                number == AnswerItem.MatchAnswer.SECOND_ANSWER_MATCH &&
                answerItem is AnswerItem.MatchAnswer
            ) {
                answerItem.copy(
                    secondAnswer = text
                )
            } else answerItem
        }
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }

    }

    fun changeCheckedAnswer(selectedId: String) = intent {
        var answerItems = getModelState().answerItem
        answerItems = answerItems.map { answerItem ->
            if (answerItem.id == selectedId && answerItem is AnswerItem.DefaultAnswer) {
                answerItem.copy(
                    isTrue = !answerItem.isTrue
                )
            } else answerItem
        }
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }
    }

    private suspend fun createAnswer(index: Int = 0, type: QuestionType): AnswerItem {
        val id = getModelState().answerItem.size + 1
        val color = getColorAnswer(index)
        return when (type) {
            QuestionType.DEFAULT -> {
                AnswerItem.DefaultAnswer(
                    id = id.toString(),
                    color = color
                )
            }

            QuestionType.MATCH -> {
                AnswerItem.MatchAnswer(
                    id = id.toString(),
                    color = color
                )
            }

            QuestionType.WRITE_ANSWER -> {
                AnswerItem.TextAnswer(
                    id = id.toString()
                )
            }
        }
    }

    private fun initQuestionType(questionType: QuestionType = initialModelState.questionTypeItem.questionType) =
        intent {
            when (questionType) {
                QuestionType.MATCH -> {
                    initAnswerMatch()
                }

                QuestionType.DEFAULT -> {
                    initAnswerDefault()
                }

                QuestionType.WRITE_ANSWER -> {
                    initAnswerWriteText()
                }
            }

        }

    private fun initAnswerMatch() = intent {
        val answer = createAnswer(index = 0, type = QuestionType.MATCH)
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    AnswerItem.FooterPlusAdd()
                )
            )
        }
    }

    private fun initAnswerWriteText() = intent {
        val answer = createAnswer(index = 0, type = QuestionType.WRITE_ANSWER)
        updateModelState {
            copy(
                answerItem = listOf(
                    answer
                )
            )
        }
    }

    private fun initAnswerDefault() = intent {
        val answer = createAnswer(
            index = 0, type = QuestionType.DEFAULT
        )
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    AnswerItem.FooterPlusAdd()
                )
            )
        }
    }

    private fun getColorAnswer(index: Int): Int {
        return when (index) {
            0 -> {
                ColorResource.MainLight.Blue.getColor(resourceHelper)
            }

            1 -> {
                ColorResource.MainLight.Green.getColor(resourceHelper)
            }

            2 -> {
                ColorResource.MainLight.Orange.getColor(resourceHelper)
            }

            3 -> {
                ColorResource.MainLight.Red.getColor(resourceHelper)
            }

            else -> {
                getColorAnswer(index - 4)
            }
        }
    }
}
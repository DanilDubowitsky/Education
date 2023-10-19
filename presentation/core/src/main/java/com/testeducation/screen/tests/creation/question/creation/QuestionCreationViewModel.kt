package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.converter.test.question.toModel
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.QuestionCreate
import com.testeducation.domain.model.question.AnswerIndicatorItem
import com.testeducation.domain.model.question.input.InputAnswer
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
import java.util.Collections

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
            answerItem = modelState.answerItem,
            time = modelState.time
        )
        router.navigateTo(NavigationScreen.Tests.Details(testId))
        postSideEffect(
            QuestionCreationSideEffect.LoaderInvisible
        )
    }

    fun openTimeDialog() {
        router.setResultListener(NavigationScreen.Questions.OnTimeQuestionChanged) { time ->
            intent {
                updateModelState {
                    copy(
                        time = time
                    )
                }
            }
        }
        intent {
            val model = getModelState()
            router.navigateTo(NavigationScreen.Questions.TimeQuestion(model.time))
        }
    }

    fun updateQuestionText(textQuestion: String) = intent {
        updateModelState {
            copy(
                questionText = textQuestion
            )
        }
    }

    fun updateSelectedDropElement(id: String) = intent {
        val answerItems = getModelState().answerItem.toMutableList()

        var currentAnswer = answerItems.find { it.id == id }
        val position = answerItems.indexOf(currentAnswer)
        if (currentAnswer is InputAnswer.OrderAnswer) {
            currentAnswer = currentAnswer.copy(
                color = ColorResource.Secondary.ColorGrayBlueDisable.getColor(resourceHelper)
            )
            answerItems[position] = currentAnswer
        }
        updateModelState {
            copy(
                selectedDropElement = currentAnswer,
                answerItem = answerItems
            )
        }
    }

    fun changeTypeQuestion() {
        router.setResultListener(NavigationScreen.Questions.OnSelectionQuestionTypeChanged) { questionTypeUiItem ->
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
        val modelState = getModelState()
        val answerItems = modelState.answerItem
        val index = answerItems.size - 1
        val answer = createAnswer(
            index, getModelState().questionTypeItem.questionType
        )
        val answerIndicator = if (modelState.questionTypeItem.questionType == QuestionType.REORDER) {
            val indicator = createAnswerIndicator(index)
            modelState.answerIndicatorItems.toMutableList().apply {
                add(indicator)
            }
        } else emptyList()
        updateModelState {
            copy(
                answerItem = answerItems.toMutableList().apply {
                    add(index, answer)
                },
                answerIndicatorItems = answerIndicator
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
            if (answerItem.id != answerId) {
                return@map answerItem
            }
            when (answerItem) {
                is InputAnswer.DefaultAnswer -> {
                    answerItem.copy(
                        answerText = text
                    )
                }

                is InputAnswer.TextAnswer -> {
                    answerItem.copy(
                        text = text
                    )
                }

                is InputAnswer.OrderAnswer -> {
                    answerItem.copy(
                        answerText = text
                    )
                }

                else -> {
                    answerItem
                }
            }
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
                number == InputAnswer.MatchAnswer.FIRST_ANSWER_MATCH &&
                answerItem is InputAnswer.MatchAnswer
            ) {
                answerItem.copy(
                    firstAnswer = text
                )
            } else if (
                answerItem.id == answerId &&
                number == InputAnswer.MatchAnswer.SECOND_ANSWER_MATCH &&
                answerItem is InputAnswer.MatchAnswer
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
            if (answerItem.id == selectedId && answerItem is InputAnswer.DefaultAnswer) {
                val newStateIsTrue = !answerItem.isTrue
                val isTrueColor = if (newStateIsTrue) {
                    ColorResource.Secondary.Gray1.getColor(resourceHelper)
                } else {
                    ColorResource.Main.White.getColor(resourceHelper)
                }
                answerItem.copy(
                    isTrue = newStateIsTrue,
                    resource = answerItem.resource.copy(
                        isTrueColor = isTrueColor
                    )
                )
            } else answerItem
        }
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }
    }

    fun updatePosition(positionCurrent: Int, targetPosition: Int) = intent {
        val modelState = getModelState()
        val answerItems = modelState.answerItem.toMutableList()
        val selectedElement = modelState.selectedDropElement

        if (selectedElement == answerItems[targetPosition]) {
            return@intent
        }
        if (positionCurrent + 1 == answerItems.size) {
            return@intent
        }
        if (targetPosition + 1 == answerItems.size) {
            return@intent
        }
        Collections.swap(answerItems, positionCurrent, targetPosition)
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }
    }

    fun clearSelectedDropElement() = intent {
        val modelState = getModelState()
        val answerItems = modelState.answerItem.toMutableList()
        modelState.answerIndicatorItems.forEachIndexed { index, answerIndicatorItem ->
            val answerItem = answerItems[index]
            if (answerItem is InputAnswer.OrderAnswer) {
                answerItems[index] = answerItem.copy(color = answerIndicatorItem.color)
            }
        }

        updateModelState {
            copy(
                selectedDropElement = null,
                answerItem = answerItems
            )
        }
    }

    private suspend fun createAnswer(index: Int = 0, type: QuestionType): InputAnswer {
        val id = getModelState().answerItem.size + 1
        val color = getColorAnswer(index)
        return when (type) {
            QuestionType.CHOICE -> {
                InputAnswer.DefaultAnswer(
                    id = id.toString(),
                    color = color,
                    resource = InputAnswer.DefaultAnswer.Resource(
                        isTrueColor = ColorResource.Main.White.getColor(resourceHelper)
                    )
                )
            }

            QuestionType.MATCH -> {
                InputAnswer.MatchAnswer(
                    id = id.toString(),
                    color = color
                )
            }

            QuestionType.TEXT -> {
                InputAnswer.TextAnswer(
                    id = id.toString()
                )
            }

            QuestionType.REORDER -> {
                InputAnswer.OrderAnswer(
                    id = id.toString(),
                    color = color,
                    order = 1
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

                QuestionType.CHOICE -> {
                    initAnswerDefault()
                }

                QuestionType.TEXT -> {
                    initAnswerWriteText()
                }

                QuestionType.REORDER -> {
                    initAnswerOrder()
                }
            }

        }

    private fun initAnswerMatch() = intent {
        val answer = createAnswer(index = 0, type = QuestionType.MATCH)
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    InputAnswer.FooterPlusAdd()
                )
            )
        }
    }

    private fun initAnswerWriteText() = intent {
        val answer = createAnswer(index = 0, type = QuestionType.TEXT)
        updateModelState {
            copy(
                answerItem = listOf(
                    answer
                )
            )
        }
    }

    private fun initAnswerOrder() = intent {
        val answer = createAnswer(index = 0, type = QuestionType.REORDER)
        val answerIndicator = createAnswerIndicator(index = 0)
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    InputAnswer.FooterPlusAdd(isOrderAnswer = true)
                ),
                answerIndicatorItems = listOf(answerIndicator)
            )
        }
    }

    private fun createAnswerIndicator(index: Int) = AnswerIndicatorItem(
        text = (index + 1).toString(),
        color = getColorAnswer(index)
    )

    private fun initAnswerDefault() = intent {
        val answer = createAnswer(
            index = 0, type = QuestionType.CHOICE
        )
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    InputAnswer.FooterPlusAdd()
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
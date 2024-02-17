package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.converter.test.answer.toInputAnswers
import com.testeducation.converter.test.question.toModel
import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestionDetails
import com.testeducation.domain.cases.question.QuestionCreate
import com.testeducation.domain.cases.question.UpdateQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.QuestionTypeUiItem
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.question.creation.utils.validateAnswerTrueAndFalse
import com.testeducation.utils.getColor
import com.testeducation.utils.getString
import com.testeducation.utils.isEmptyOrBlank
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
    private val updateQuestion: UpdateQuestion,
    private val testId: String,
    private val orderQuestion: Int,
    private val questionId: String,
    private val getQuestionDetails: GetQuestionDetails
) : BaseViewModel<QuestionCreationModelState, QuestionCreationState, QuestionCreationSideEffect>(
    reducer,
    errorHandler
) {

    companion object {
        private const val ERROR_NUMBER_TEXT = "The number cannot be more than 2"
        private const val MAX_ANSWER = 7
    }

    override val initialModelState: QuestionCreationModelState = QuestionCreationModelState(
        questionTypeItem = questionTypeItem.toModel()
    )

    init {
        if (questionId.isNotEmpty()) {
            getQuestionDetails()
        } else {
            initQuestionType()
        }
    }

    override fun handleThrowable(throwable: Throwable) {
        intent {
            postSideEffect(
                QuestionCreationSideEffect.LoaderInvisible
            )
        }
        super.handleThrowable(throwable)
    }

    private fun getQuestionDetails() {
        intent {
            updateModelState {
                copy(
                    loadingScreen = true
                )
            }
            getQuestionDetails.invoke(testId, questionId).also { result ->
                val answers = when (result) {
                    is Question.Choice -> result.answers
                    is Question.Match -> result.answers
                    is Question.Order -> result.answers
                    is Question.Text -> emptyList()
                }

                updateModelState {
                    copy(
                        answerItem = answers.toInputAnswers(
                            ::getColorAnswer,
                            ::getTrueColor
                        ).plus(InputAnswer.FooterPlusAdd()),
                        questionText = result.title,
                        time = result.time,
                        loadingScreen = false
                    )
                }
            }
        }
    }

    fun saveQuestion() = intent {
        val modelState = getModelState()
        doIfValidQuestion(modelState) {
            intent {
                postSideEffect(
                    QuestionCreationSideEffect.LoaderVisible
                )
                if (questionId.isNotEmpty()) {
                    updateQuestion(
                        questionId = questionId,
                        testId = testId,
                        type = modelState.questionTypeItem.questionType,
                        questionText = modelState.questionText,
                        answerItem = modelState.answerItem,
                        time = modelState.time,
                        orderQuestion = orderQuestion
                    )
                } else {
                    questionCreate(
                        testId = testId,
                        type = modelState.questionTypeItem.questionType,
                        questionText = modelState.questionText,
                        answerItem = modelState.answerItem,
                        time = modelState.time,
                        orderQuestion = orderQuestion
                    )
                }
                router.navigateTo(NavigationScreen.Tests.Details(testId), false)
                postSideEffect(
                    QuestionCreationSideEffect.LoaderInvisible
                )
            }
        }
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
        val questionText = getModelState().questionText
        if (questionText == textQuestion) return@intent
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
        val newAnswerItems = answerItems.toMutableList().apply {
            add(index, answer)
        }
        updateModelState {
            copy(
                answerItem = newAnswerItems,
                visibleAddFooter = newAnswerItems.size < MAX_ANSWER
            )
        }
    }

    fun deleteAnswer(selectedId: String) = intent {
        val answerItems = getModelState().answerItem.toMutableList()
        val answerForDelete = answerItems.find { item ->
            item.id == selectedId
        }
        answerForDelete?.let { answerItemNotNull ->
            val newAnswerItems = answerItems.apply {
                remove(answerItemNotNull)
            }
            updateModelState {
                copy(
                    answerItem = newAnswerItems.updateColor(),
                    visibleAddFooter = newAnswerItems.size < MAX_ANSWER
                )
            }
        }
    }

    fun openAnswerInput(answerId: String, firstAnswer: Boolean = true) {
        router.setResultListener(NavigationScreen.Questions.AnswerInput.OnAnswerInput) { result ->
            intent {
                var answerItems = getModelState().answerItem
                answerItems = answerItems.map { answerItem ->
                    if (answerItem.id != answerId) {
                        return@map answerItem
                    }
                    when (answerItem) {
                        is InputAnswer.DefaultAnswer -> {
                            answerItem.copy(
                                answerText = result.answerText
                            )
                        }

                        is InputAnswer.TextAnswer -> {
                            answerItem.copy(
                                text = result.answerText
                            )
                        }

                        is InputAnswer.OrderAnswer -> {
                            answerItem.copy(
                                answerText = result.answerText
                            )
                        }

                        is InputAnswer.MatchAnswer -> {
                            answerItem.copy(
                                firstAnswer = if (result.firstAnswer) result.answerText else answerItem.firstAnswer,
                                secondAnswer = if (!result.firstAnswer) result.answerText else answerItem.secondAnswer
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
        }
        intent {
            getModelState().answerItem.find { it.id == answerId }?.let { itemAnswer ->
                if (itemAnswer is InputAnswer.FooterPlusAdd) {
                    return@intent
                }
                val textAndColorPair = when (itemAnswer) {
                    is InputAnswer.DefaultAnswer -> Pair(itemAnswer.answerText, itemAnswer.color)
                    is InputAnswer.OrderAnswer -> Pair(itemAnswer.answerText, itemAnswer.color)
                    is InputAnswer.MatchAnswer -> {
                        val answer = if (firstAnswer) itemAnswer.firstAnswer else itemAnswer.secondAnswer
                        Pair(answer, itemAnswer.color)
                    }
                    is InputAnswer.TextAnswer -> Pair(
                        itemAnswer.text,
                        ColorResource.MainLight.Green.getColor(resourceHelper)
                    )

                    else -> Pair("", 0)
                }
                postSideEffect(QuestionCreationSideEffect.ClearFocus)
                router.navigateTo(
                    NavigationScreen.Questions.AnswerInput(
                        textAndColorPair.first,
                        textAndColorPair.second,
                        firstAnswer
                    )
                )
            }
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
                val isTrueColor = getTrueColor(newStateIsTrue)
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
        val answerItemsUpdatedPosition = answerItems.mapIndexed { index, inputAnswer ->
            if (inputAnswer is InputAnswer.OrderAnswer) {
                inputAnswer.copy(
                    order = index + 1
                )
            } else inputAnswer
        }
        if (selectedElement is InputAnswer.OrderAnswer) {
            updateModelState {
                copy(
                    answerItem = answerItemsUpdatedPosition,
                    selectedDropElement = selectedElement.copy(
                        order = targetPosition + 1
                    )
                )
            }
        }
    }

    fun clearSelectedDropElement() = intent {
        val modelState = getModelState()
        val answerItems = modelState.answerItem.toMutableList()

        updateModelState {
            copy(
                selectedDropElement = null,
                answerItem = answerItems.updateColor()
            )
        }
    }

    private fun doIfValidQuestion(state: QuestionCreationModelState, action: () -> Unit) = intent {
        if (state.questionText.isEmptyOrBlank()) {
            val screen = NavigationScreen.Common.Information(
                titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                    resourceHelper
                ),
                description = StringResource.Validate.EmptyQuestionCreation.getString(resourceHelper),
                btnText = StringResource.Common.CommonNext.getString(resourceHelper),
            )
            router.navigateTo(screen)
            return@intent
        }
        if (state.answerItem.isEmpty()) {
            val screen = NavigationScreen.Common.Information(
                titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                    resourceHelper
                ),
                description = StringResource.Validate.OneAnswerQuestionCreation.getString(
                    resourceHelper
                ),
                btnText = StringResource.Common.CommonNext.getString(resourceHelper),
            )
            router.navigateTo(screen)
            return@intent
        }
        when (state.questionTypeItem.questionType) {
            QuestionType.CHOICE -> {
                if (state.answerItem.size - 1 < 2) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.MinCountAnswer(2)
                            .getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
                if (state.answerItem.find { it is InputAnswer.DefaultAnswer && it.isTrue } == null) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.MinOneTrueAnswer.getString(
                            resourceHelper
                        ),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
                if (state.answerItem.find { it is InputAnswer.DefaultAnswer && it.answerText.isEmptyOrBlank() } != null) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.AnswerIsEmpty.getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }

                state.answerItem.validateAnswerTrueAndFalse(resourceHelper) { error ->
                    router.navigateTo(error)
                    return@intent
                }

            }

            QuestionType.REORDER -> {
                if (state.answerItem.size - 1 < 3) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.MinCountAnswer(3)
                            .getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
                if (state.answerItem.find { it is InputAnswer.OrderAnswer && it.answerText.isEmptyOrBlank() } != null) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.AnswerIsEmpty.getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
            }

            QuestionType.MATCH -> {
                if (state.answerItem.size - 1 < 3) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.MinCountAnswer(3)
                            .getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
                if (state.answerItem.find {
                        it is InputAnswer.MatchAnswer && it.firstAnswer.isEmptyOrBlank() ||
                                it is InputAnswer.MatchAnswer && it.secondAnswer.isEmptyOrBlank()
                    } != null) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.AnswerIsEmpty.getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
            }

            QuestionType.TEXT -> {
                val textAnswer = state.answerItem.first()
                if (textAnswer is InputAnswer.TextAnswer && textAnswer.text.isEmptyOrBlank()) {
                    val screen = NavigationScreen.Common.Information(
                        titleText = StringResource.Validate.QuestionCreationErrorTitle.getString(
                            resourceHelper
                        ),
                        description = StringResource.Validate.AnswerIsEmpty.getString(resourceHelper),
                        btnText = StringResource.Common.CommonNext.getString(resourceHelper),
                    )
                    router.navigateTo(screen)
                    return@intent
                }
            }
        }
        action()
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
                    order = index + 1
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
        updateModelState {
            copy(
                answerItem = listOf(
                    answer,
                    InputAnswer.FooterPlusAdd(isOrderAnswer = true)
                ),
            )
        }
    }

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

    private fun getTrueColor(isTrue: Boolean) = if (isTrue) {
        ColorResource.Secondary.Gray1.getColor(resourceHelper)
    } else {
        ColorResource.Main.White.getColor(resourceHelper)
    }

    private fun List<InputAnswer>.updateColor() = mapIndexed { index, answer ->
        when (answer) {
            is InputAnswer.OrderAnswer -> answer.copy(color = getColorAnswer(index))
            is InputAnswer.MatchAnswer -> answer.copy(color = getColorAnswer(index))
            is InputAnswer.DefaultAnswer -> answer.copy(color = getColorAnswer(index))
            else -> answer
        }
    }
}
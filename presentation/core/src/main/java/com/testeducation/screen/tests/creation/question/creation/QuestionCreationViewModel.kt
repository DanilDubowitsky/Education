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

class QuestionCreationViewModel(
    reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper,
    questionTypeItem: QuestionTypeUiItem,
    private val router: NavigationRouter,
    private val questionCreate: QuestionCreate
) : BaseViewModel<QuestionCreationModelState, QuestionCreationState, QuestionCreationSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: QuestionCreationModelState = QuestionCreationModelState(
        questionTypeItem = questionTypeItem.toModel()
    )

    init {
        initQuestionType()
    }

    fun saveQuestion() {

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
        val answer = addDefaultAnswer(
            index
        )
        updateModelState {
            copy(
                answerItem = answerItems.toMutableList().apply {
                    add(index, answer)
                }
            )
        }
    }

    fun deleteAnswer(selectedId: Int) = intent {
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

    fun answerTextChanger(answerId: Int, text: String) = intent {
        var answerItems = getModelState().answerItem
        answerItems = answerItems.map { answerItem ->
            if (answerItem.id == answerId && answerItem is AnswerItem.DefaultAnswer) {
                answerItem.copy(
                    answerText = text
                )
            } else answerItem
        }
        updateModelState {
            copy(
                answerItem = answerItems
            )
        }
    }

    fun changeCheckedAnswer(selectedId: Int) = intent {
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

    private suspend fun addDefaultAnswer(index: Int = 0) = AnswerItem.DefaultAnswer(
        id = getModelState().answerItem.size + 1,
        color = getColorAnswer(index)
    )

    private fun initQuestionType(questionType: QuestionType = initialModelState.questionTypeItem.questionType) =
        intent {
            when (questionType) {
                QuestionType.MATCH -> {

                }

                QuestionType.DEFAULT -> {
                    initAnswerDefault()
                }

                QuestionType.WRITE_ANSWER -> {
                    initAnswerWriteText()
                }
            }

        }

    private fun initAnswerWriteText() = intent {
        val id = getModelState().answerItem.size
        updateModelState {
            copy(
                answerItem = listOf(
                    AnswerItem.TextAnswer(
                        id = id
                    )
                )
            )
        }
    }

    private fun initAnswerDefault() = intent {
        val answer = addDefaultAnswer()
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
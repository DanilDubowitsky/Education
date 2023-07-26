package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.QuestionCreationState
import com.testeducation.utils.getColor
import org.orbitmvi.orbit.syntax.simple.intent

class QuestionCreationViewModel(
    reducer: IReducer<QuestionCreationModelState, QuestionCreationState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper
) : BaseViewModel<QuestionCreationModelState, QuestionCreationState, QuestionCreationSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: QuestionCreationModelState = QuestionCreationModelState()

    init {
        initAnswerDefault()
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
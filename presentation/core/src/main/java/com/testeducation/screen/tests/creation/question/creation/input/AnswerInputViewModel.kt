package com.testeducation.screen.tests.creation.question.creation.input

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.screen.tests.creation.question.creation.input.AnswerInputSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.input.AnswerInputState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent

class AnswerInputViewModel(
    reducer: IReducer<AnswerInputModelState, AnswerInputState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper,
    private val router: NavigationRouter,
    private val answerText: String,
    private val color: Int,
    private val firstAnswer: Boolean
) : BaseViewModel<AnswerInputModelState, AnswerInputState, AnswerInputSideEffect>(
    reducer,
    errorHandler
) {

    companion object {
        private const val MAX_LENGTH = 128
    }

    override val initialModelState: AnswerInputModelState = AnswerInputModelState()

    init {
        init()
    }

    fun updateAnswerText(answerText: String) = intent {
        updateModelState {
            copy(
                answerText = answerText,
                maxLengthText =  StringResource.Question.MaxLengthAnswer(
                    MAX_LENGTH - answerText.length
                ).getString(resourceHelper)
            )
        }
    }

    fun save() = intent {
        val answerCurrent = getModelState().answerText
        router.sendResult(
            NavigationScreen.Questions.AnswerInput.OnAnswerInput,
            NavigationScreen.Questions.AnswerInput.OnAnswerInput.Result(
                answerCurrent, firstAnswer
            )
        )
        router.exit()
    }

    private fun init() = intent {
        updateModelState {
            copy(
                answerText = this@AnswerInputViewModel.answerText,
                color = this@AnswerInputViewModel.color,
                maxLengthText =  StringResource.Question.MaxLengthAnswer(
                    MAX_LENGTH - answerText.length
                ).getString(resourceHelper)
            )
        }
    }
}
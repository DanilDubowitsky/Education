package com.testeducation.screen.tests.creation.question.creation.time

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.TimeQuestion
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.question.TimeQuestionUi
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TimeQuestionViewModel(
    reducer: IReducer<TimeQuestionModelState, TimeQuestionState>,
    errorHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val currentTimeQuestion: Long,
) : BaseViewModel<TimeQuestionModelState, TimeQuestionState, TimeQuestionSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: TimeQuestionModelState = TimeQuestionModelState()

    init {
        intent {
            updateModelState {
                copy(
                    timeList = prepareTimeData()
                )
            }
        }
    }

    fun submit(timeQuestionUi: TimeQuestionUi) {

    }

    private fun prepareTimeData() : List<TimeQuestion> {
        val timeList = mutableListOf<TimeQuestion>()
        for (i in 1..18) {
            val time = (i * 10).toLong()
            val timeQuestion = TimeQuestion(
                time = time,
                isSelected = time == currentTimeQuestion
            )
            timeList.add(
                timeQuestion
            )
        }
        return timeList
    }

}
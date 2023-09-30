package com.testeducation.screen.tests.creation.question.creation.time

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.TimeQuestion
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionSideEffect
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.utils.getColor
import org.orbitmvi.orbit.syntax.simple.intent

class TimeQuestionViewModel(
    reducer: IReducer<TimeQuestionModelState, TimeQuestionState>,
    errorHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val currentTimeQuestion: Long,
    private val resourceHelper: IResourceHelper
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

    fun submit(position: Int) = intent {
        val modelState = getModelState()
        val timeList = modelState.timeList
        updateModelState {
            copy(
                timeList = timeList.mapIndexed { index, time ->
                    val isSelected = index == position
                    val color = getColorSelected(isSelected)
                    time.copy(
                        isSelected = isSelected,
                        color = color
                    )
                }
            )
        }
    }

    private fun prepareTimeData(): List<TimeQuestion> {
        val timeList = mutableListOf<TimeQuestion>()
        for (i in 0..18) {
            val time = (i * 10).toLong()
            val isSelected = time == currentTimeQuestion
            val color = getColorSelected(isSelected)
            val timeQuestion = TimeQuestion(
                time = time,
                isSelected = isSelected,
                color = color
            )
            timeList.add(
                timeQuestion
            )
        }
        return timeList
    }

    private fun getColorSelected(isSelected: Boolean) = if (isSelected) {
        ColorResource.Secondary.ColorDarkBlue.getColor(resourceHelper = resourceHelper)
    } else ColorResource.Main.Black.getColor(resourceHelper = resourceHelper)

}
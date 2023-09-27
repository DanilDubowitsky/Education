package com.testeducation.screen.tests.creation.question.creation.time

import com.testeducation.core.IReducer
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.model.question.TimeQuestionUi
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionState

class TimeQuestionReducer(private val timeConverterLongToString: ITimeConverterLongToString) :
    IReducer<TimeQuestionModelState, TimeQuestionState> {

    override fun reduce(modelState: TimeQuestionModelState): TimeQuestionState {
        return TimeQuestionState(
            timeList = modelState.timeList.map {
                TimeQuestionUi(
                    time = timeConverterLongToString.convert(it.time),
                    isSelected = it.isSelected
                )
            }
        )
    }
}
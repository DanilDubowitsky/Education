package com.testeducation.screen.tests.pass.answer

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.answer.FullAnswerSideEffect
import com.testeducation.logic.screen.tests.pass.answer.FullAnswerState

class FullAnswerViewModel(
    reducer: IReducer<FullAnswerModelState, FullAnswerState>,
    exceptionHandler: IExceptionHandler,
    answerText: String,
    color: Int
) : BaseViewModel<FullAnswerModelState,
        FullAnswerState, FullAnswerSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: FullAnswerModelState = FullAnswerModelState(answerText, color)

}

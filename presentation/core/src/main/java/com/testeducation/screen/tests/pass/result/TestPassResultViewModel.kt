package com.testeducation.screen.tests.pass.result

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.result.TestPassResultSideEffect
import com.testeducation.logic.screen.tests.pass.result.TestPassResultState
import org.orbitmvi.orbit.syntax.simple.intent

class TestPassResultViewModel(
    reducer: IReducer<TestPassResultModelState, TestPassResultState>,
    exceptionHandler: IExceptionHandler,
    correctAnswers: Int,
    incorrectAnswers: Int
) : BaseViewModel<TestPassResultModelState, TestPassResultState, TestPassResultSideEffect>(
        reducer,
        exceptionHandler
    ) {

    override val initialModelState: TestPassResultModelState = TestPassResultModelState(
        correctAnswers = correctAnswers,
        incorrectAnswers = incorrectAnswers
    )

    fun openFullStatistic() = intent {

    }

}

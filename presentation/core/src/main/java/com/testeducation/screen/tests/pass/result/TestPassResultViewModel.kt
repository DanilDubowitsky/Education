package com.testeducation.screen.tests.pass.result

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.result.TestPassResultSideEffect
import com.testeducation.logic.screen.tests.pass.result.TestPassResultState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestPassResultViewModel(
    reducer: IReducer<TestPassResultModelState, TestPassResultState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    correctAnswers: Int,
    incorrectAnswers: Int,
    isSuccess: Boolean
) : BaseViewModel<TestPassResultModelState, TestPassResultState, TestPassResultSideEffect>(
        reducer,
        exceptionHandler
    ) {

    override val initialModelState: TestPassResultModelState = TestPassResultModelState(
        correctAnswers = correctAnswers,
        incorrectAnswers = incorrectAnswers,
        isSuccess = isSuccess
    )

    fun openFullStatistic() = intent {

    }

    fun returnToMainPage() = intent {
        router.exit()
    }

}

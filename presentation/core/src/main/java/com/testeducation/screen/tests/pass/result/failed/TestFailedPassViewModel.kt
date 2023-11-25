package com.testeducation.screen.tests.pass.result.failed

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.result.failed.TestFailedPassSideEffect
import com.testeducation.logic.screen.tests.pass.result.failed.TestFailedPassState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen

class TestFailedPassViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestFailedPassModelState, TestFailedPassState>,
    private val router: NavigationRouter,
    isCheating: Boolean
) : BaseViewModel<TestFailedPassModelState, TestFailedPassState, TestFailedPassSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestFailedPassModelState =
        TestFailedPassModelState(isCheating = isCheating)

    fun backToMainPaige() {
        router.sendResult(NavigationScreen.Tests.Result.OpenMainPage, Unit)
        router.exit()
    }

}

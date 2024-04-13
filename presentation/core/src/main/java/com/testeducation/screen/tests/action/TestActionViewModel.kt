package com.testeducation.screen.tests.action

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.action.TestActionSideEffect
import com.testeducation.logic.screen.tests.action.TestActionState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class TestActionViewModel(
    reducer: IReducer<TestActionModelState, TestActionState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testTitle: String,
    private val testId: String,
    private val isOwner: Boolean,
    isPassed: Boolean,
    private val color: String,
) : BaseViewModel<TestActionModelState, TestActionState, TestActionSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestActionModelState = TestActionModelState(
        testTitle = testTitle,
        isOwner = isOwner,
        isPassed = isPassed
    )

    fun onInfoClick() = intent {
        val screen = NavigationScreen.Tests.Preview(testId)
        router.exit()
        router.navigateTo(screen)
    }

    fun onActionClick() = intent {
        val screen = if (isOwner) {
            NavigationScreen.Tests.Details(
                testId,
                navigateFrom = NavigationScreen.Tests.Details.NavigateFrom.MyLibrary
            )
        } else {
            NavigationScreen.Tests.Statistic(testId, false, testTitle, color)
        }
        router.exit()
        router.navigateTo(screen)
    }

}

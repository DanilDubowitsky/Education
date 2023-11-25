package com.testeducation.screen.tests.statistic

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetTestPassStatistic
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticSideEffect
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class TestPassStatisticViewModel(
    reducer: IReducer<TestPassStatisticModelState, TestPassStatisticState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    private val testId: String,
    private val getTestPassStatistic: GetTestPassStatistic
) : BaseViewModel<TestPassStatisticModelState, TestPassStatisticState, TestPassStatisticSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestPassStatisticModelState = TestPassStatisticModelState()

    init {
        loadData()
    }

    fun exit() {
        router.exit()
    }

    fun changeItemExpandState(id: String) = intent {
        val modelState = getModelState()
        val newList = if (modelState.expandedItems.contains(id)) {
            modelState.expandedItems - id
        } else {
            modelState.expandedItems + id
        }
        updateModelState {
            copy(expandedItems = newList)
        }
    }

    private fun loadData() = intent {
        val statistic = getTestPassStatistic(testId)
        updateModelState {
            copy(statistic = statistic)
        }
    }

}

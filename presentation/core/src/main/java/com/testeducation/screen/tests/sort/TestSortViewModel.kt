package com.testeducation.screen.tests.sort

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.sort.TestSortSideEffect
import com.testeducation.logic.screen.tests.sort.TestSortState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class TestSortViewModel(
    reducer: IReducer<TestSortModelState, TestSortState>,
    exceptionHandler: IExceptionHandler,
    private val router: NavigationRouter,
    selectedSort: TestOrderField,
    sortDirection: OrderDirection
) : BaseViewModel<TestSortModelState, TestSortState, TestSortSideEffect>(
    reducer,
    exceptionHandler
) {

    override val initialModelState: TestSortModelState =
        TestSortModelState(
            selectedField = selectedSort,
            direction = sortDirection
        )

    fun changeSort(sort: String, direction: String) = intent {
        val modelSort = TestOrderField.valueOf(sort)
        val modelDirection = OrderDirection.valueOf(direction)
        updateModelState {
            copy(selectedField = modelSort, direction = modelDirection)
        }
    }

    fun applySort() = intent {
        val modelState = getModelState()
        router.sendResult(
            NavigationScreen.Tests.TestSort.OnSortChanged,
            NavigationScreen.Tests.TestSort.SortValues(
                modelState.selectedField.name,
                modelState.direction.name
            )
        )
        router.exit()
    }
}

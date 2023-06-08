package com.testeducation.screen.tests.filters

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.filters.TestsFiltersSideEffect
import com.testeducation.logic.screen.tests.filters.TestsFiltersState

class TestsFiltersViewModel(
    reducer: IReducer<TestsFiltersModelState, TestsFiltersState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<TestsFiltersModelState, TestsFiltersState, TestsFiltersSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestsFiltersModelState = TestsFiltersModelState()
}
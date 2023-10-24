package com.testeducation.screen.tests.edit.style

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.screen.tests.settings.TestSettingsSideEffect
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState

class TestStyleChangerViewModel(
    exceptionHandler: IExceptionHandler,
    reducer: IReducer<TestStyleChangerModelState, TestStyleChangerState>,
) : BaseViewModel<TestStyleChangerModelState, TestStyleChangerState, TestSettingsSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: TestStyleChangerModelState = TestStyleChangerModelState()

    fun changeStyleTestCard(cardTestStyle: CardTestStyle) {

    }
}
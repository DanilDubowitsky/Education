package com.testeducation.screen.tests.edit.style

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.settings.TestStyleChangerState

class TestStyleChangerReducer : IReducer<TestStyleChangerModelState, TestStyleChangerState> {
    override fun reduce(modelState: TestStyleChangerModelState): TestStyleChangerState {
        return TestStyleChangerState(
            //TODO сделать конвертер
            iconDesignList = modelState.iconDesign
        )
    }

}
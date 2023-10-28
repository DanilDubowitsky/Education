package com.testeducation.screen.tests.edit.settings

import com.testeducation.converter.test.toUi
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.settings.TestSettingsState

class TestSettingsReducer : IReducer<TestSettingsModelState, TestSettingsState> {
    override fun reduce(modelState: TestSettingsModelState): TestSettingsState {
        val testElementListUi = modelState.testElementList.toUi().toMutableList()
        return TestSettingsState(
            testSettingsElements = testElementListUi
        )
    }

}
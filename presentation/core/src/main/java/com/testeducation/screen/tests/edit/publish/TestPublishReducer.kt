package com.testeducation.screen.tests.edit.publish

import com.testeducation.converter.test.convertToUi
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.edit.TestPublishState

class TestPublishReducer() :
    IReducer<TestPublishModelState, TestPublishState> {
    override fun reduce(modelState: TestPublishModelState): TestPublishState {
        return TestPublishState(
            statusPublish = modelState.status.convertToUi()
        )
    }
}
package com.testeducation.screen.tests.code.share

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState

class TestCodeShareReducer : IReducer<TestCodeShareModelState, TestCodeShareState> {

    override fun reduce(modelState: TestCodeShareModelState): TestCodeShareState {
        return TestCodeShareState()
    }
}

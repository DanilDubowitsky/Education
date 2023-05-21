package com.example.screen.tests

import com.example.core.IReducer

class TestsReducer : IReducer<TestsModelState, TestsState> {

    override fun reduce(modelState: TestsModelState): TestsState {
        return TestsState()
    }

}

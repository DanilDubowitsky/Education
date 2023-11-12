package com.testeducation.screen.tests.statistic

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState

class TestPassStatisticReducer : IReducer<TestPassStatisticModelState, TestPassStatisticState> {

    override fun reduce(modelState: TestPassStatisticModelState): TestPassStatisticState {

        return TestPassStatisticState()
    }
}

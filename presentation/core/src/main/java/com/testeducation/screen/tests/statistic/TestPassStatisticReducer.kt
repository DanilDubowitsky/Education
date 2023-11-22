package com.testeducation.screen.tests.statistic

import com.testeducation.converter.test.question.toUI
import com.testeducation.converter.test.question.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState

class TestPassStatisticReducer : IReducer<TestPassStatisticModelState, TestPassStatisticState> {

    override fun reduce(modelState: TestPassStatisticModelState): TestPassStatisticState {
        val questionsUI = modelState.statistic.toExpandableUI(modelState.expandedItems)
        return TestPassStatisticState(questionsUI)
    }

    private fun List<AnsweredQuestion>.toExpandableUI(expandedIds: List<String>) = map { question ->
        val isExpanded = expandedIds.contains(question.id)
        question.toUI(isExpanded)
    }
}

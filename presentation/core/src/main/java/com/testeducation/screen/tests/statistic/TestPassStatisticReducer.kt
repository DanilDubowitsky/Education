package com.testeducation.screen.tests.statistic

import com.testeducation.converter.test.question.toUI
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState
import com.testeducation.utils.MINUTES_SECONDS_FORMAT
import com.testeducation.utils.formatDateInSeconds

class TestPassStatisticReducer : IReducer<TestPassStatisticModelState, TestPassStatisticState> {

    override fun reduce(modelState: TestPassStatisticModelState): TestPassStatisticState {
        val questionsUI = modelState.testPassResult?.answers?.toExpandableUI(
            modelState.expandedItems,
            modelState.trueAnswersExpanded
        ) ?: emptyList()

        val passTime = modelState.testPassResult?.timeSpent ?: 0L
        val isSuccess = modelState.testPassResult?.success ?: false
        val trueAnswers = modelState.testPassResult?.trueAnswers ?: 0
        val falseAnswers = modelState.testPassResult?.falseAnswers ?: 0

        val isLoading = modelState.loadingState == TestPassStatisticModelState.LoadingState.LOADING
        return TestPassStatisticState(
            questions = questionsUI,
            testTitle = modelState.testTitle,
            passTime = passTime,
            isSuccess = isSuccess,
            trueAnswers = trueAnswers,
            falseAnswers = falseAnswers,
            isLoading = isLoading,
            testColor = modelState.testColor
        )
    }

    private fun List<AnsweredQuestion>.toExpandableUI(
        expandedIds: List<String>,
        trueAnswersExpandedIds: List<String>
    ) = map { question ->
        val isExpanded = expandedIds.contains(question.id)
        val isTrueExpanded = trueAnswersExpandedIds.contains(question.id)
        question.toUI(isExpanded, isTrueExpanded)
    }
}

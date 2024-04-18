package com.testeducation.screen.tests.statistic

import com.testeducation.converter.test.question.toUI
import com.testeducation.core.IReducer
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.result.TestPassResult
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState

class TestPassStatisticReducer : IReducer<TestPassStatisticModelState, TestPassStatisticState> {

    override fun reduce(modelState: TestPassStatisticModelState): TestPassStatisticState {
        val questionsUI = modelState.testPassResult?.answers?.toExpandableUI(
            modelState.expandedItems,
            modelState.trueAnswersExpanded
        ) ?: emptyList()

        val passTime = modelState.testPassResult?.timeSpent ?: 0L
        val trueAnswers = modelState.testPassResult?.trueAnswers ?: 0
        val falseAnswers = modelState.testPassResult?.falseAnswers ?: 0

        val isLoading = modelState.loadingState == TestPassStatisticModelState.LoadingState.LOADING
        return TestPassStatisticState(
            questions = questionsUI,
            testTitle = modelState.testTitle,
            passTime = passTime,
            result = modelState.testPassResult?.result?.toUI()
                ?: TestPassStatisticState.TestPassResultUI.FAILED,
            trueAnswers = trueAnswers,
            falseAnswers = falseAnswers,
            isLoading = isLoading,
            testColor = modelState.testColor
        )
    }

    private fun TestPassResult.ResultStatus.toUI() = when (this) {
        TestPassResult.ResultStatus.SUCCESSFUL -> TestPassStatisticState.TestPassResultUI.SUCCESSFUL
        TestPassResult.ResultStatus.FAILED -> TestPassStatisticState.TestPassResultUI.FAILED
        TestPassResult.ResultStatus.FAILED_MIN_QUESTIONS -> {
            TestPassStatisticState.TestPassResultUI.FAILED_MIN_QUESTIONS
        }

        TestPassResult.ResultStatus.FAILED_CHEATING -> {
            TestPassStatisticState.TestPassResultUI.FAILED_CHEATING
        }
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

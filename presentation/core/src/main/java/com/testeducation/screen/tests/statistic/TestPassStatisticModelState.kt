package com.testeducation.screen.tests.statistic

import com.testeducation.domain.cases.question.GetTestPassResult

data class TestPassStatisticModelState(
    val testPassResult: GetTestPassResult.PassResultWithAnsweredQuestions? = null,
    val expandedItems: List<String> = emptyList(),
    val trueAnswersExpanded: List<String> = emptyList(),
    val loadingState: LoadingState = LoadingState.LOADING,
    val testTitle: String = "",
    val testColor: String = "#1CCD9D"
) {

    enum class LoadingState {
        LOADING,
        IDLE
    }
}

package com.testeducation.logic.screen.tests.statistic

import com.testeducation.logic.model.question.AnsweredQuestionUI

data class TestPassStatisticState(
    val questions: List<AnsweredQuestionUI>,
    val testTitle: String,
    val passTime: Long,
    val result: TestPassResultUI,
    val trueAnswers: Int,
    val falseAnswers: Int,
    val isLoading: Boolean,
    val testColor: String
) {
    enum class TestPassResultUI {
        SUCCESSFUL,
        FAILED,
        FAILED_MIN_QUESTIONS,
        FAILED_CHEATING
    }
}

package com.testeducation.logic.screen.tests.pass

import com.testeducation.logic.model.question.QuestionUI

data class TestPassingState(
    val currentQuestion: QuestionUI?,
    val matchData: List<MatchDataUI>
) {
    data class MatchDataUI(
        val text: String,
        val color: Int
    )
}
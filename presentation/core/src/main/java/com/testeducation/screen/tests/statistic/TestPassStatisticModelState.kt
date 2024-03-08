package com.testeducation.screen.tests.statistic

import com.testeducation.domain.model.question.answered.AnsweredQuestion

data class TestPassStatisticModelState(
    val statistic: List<AnsweredQuestion> = emptyList(),
    val expandedItems: List<String> = emptyList(),
    val trueAnswersExpanded: List<String> = emptyList()
)

package com.testeducation.screen.tests.creation.question.creation.time

import com.testeducation.domain.model.question.TimeQuestion

data class TimeQuestionModelState(
    val timeList: List<TimeQuestion> = emptyList()
)
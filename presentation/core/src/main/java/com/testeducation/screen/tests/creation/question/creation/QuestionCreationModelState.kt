package com.testeducation.screen.tests.creation.question.creation

import com.testeducation.domain.model.question.AnswerItem

data class QuestionCreationModelState(
    val answerItem: List<AnswerItem> = emptyList()
)
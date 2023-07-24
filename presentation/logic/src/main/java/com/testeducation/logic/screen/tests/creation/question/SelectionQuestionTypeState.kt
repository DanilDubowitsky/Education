package com.testeducation.logic.screen.tests.creation.question

import com.testeducation.logic.model.test.QuestionTypeUiItem

data class SelectionQuestionTypeState(
    val questionTypeUiItems: List<QuestionTypeUiItem> = emptyList()
)
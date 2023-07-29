package com.testeducation.screen.tests.creation.question

import com.testeducation.domain.model.question.QuestionTypeItem
import com.testeducation.logic.model.test.QuestionTypeUiItem

data class SelectionQuestionTypeModelState(
    val questionTypeItems: List<QuestionTypeItem> = emptyList()
)
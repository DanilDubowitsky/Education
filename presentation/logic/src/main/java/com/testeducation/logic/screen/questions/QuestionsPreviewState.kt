package com.testeducation.logic.screen.questions

import com.testeducation.logic.model.question.QuestionItemUi

data class QuestionsPreviewState(
    val questions: List<QuestionItemUi>,
    val isLoading: Boolean
)

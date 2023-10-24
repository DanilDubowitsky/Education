package com.testeducation.logic.screen.questions

import com.testeducation.logic.model.question.QuestionPreviewUI

data class QuestionsPreviewState(
    val questions: List<QuestionPreviewUI>,
    val isLoading: Boolean
)

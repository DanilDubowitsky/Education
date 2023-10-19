package com.testeducation.logic.screen.questions

import com.testeducation.logic.model.question.QuestionUI

data class QuestionsPreviewState(
    val questions: List<QuestionUI>,
    val isLoading: Boolean
)

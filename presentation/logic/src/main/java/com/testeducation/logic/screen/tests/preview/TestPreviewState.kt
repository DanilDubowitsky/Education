package com.testeducation.logic.screen.tests.preview

import com.testeducation.logic.model.question.QuestionItemUi

data class TestPreviewState(
    val isLoading: Boolean,
    val theme: String,
    val title: String,
    val createdDate: String,
    val isLiked: Boolean,
    val description: String,
    val questions: List<QuestionItemUi>,
    val allowPreviewQuestions: Boolean,
    val isQuestionsShown: Boolean,
    val creatorName: String,
    val isExpandButtonVisible: Boolean,
    val isExpand: Boolean
)

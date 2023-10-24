package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerUI

data class QuestionPreviewUI(
    val id: String,
    val title: String,
    val numberQuestion: String,
    val answerItemUiList: List<AnswerUI>,
    val time: String
)

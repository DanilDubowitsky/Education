package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerCreationUI
import com.testeducation.logic.model.test.QuestionTypeUiItem

data class InputQuestionUI(
    val id: String,
    val title: String,
    val icon: Int,
    val numberQuestion: String,
    val answerItemUiList: List<AnswerCreationUI>,
    val questionTypeUiItem: QuestionTypeUiItem,
    val time: String
)

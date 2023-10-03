package com.testeducation.logic.model.question

import com.testeducation.logic.model.test.AnswerItemUi
import com.testeducation.logic.model.test.QuestionTypeUiItem

data class QuestionItemUi(
    val id: String,
    val title: String,
    val icon: Int,
    val numberQuestion: String,
    val answerItemUiList: List<AnswerItemUi>,
    val questionTypeUiItem: QuestionTypeUiItem,
    val time: String
)
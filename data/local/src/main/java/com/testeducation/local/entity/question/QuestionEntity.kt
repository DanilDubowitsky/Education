package com.testeducation.local.entity.question

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.domain.model.question.QuestionType

data class QuestionEntity(
    val id: String,
    val title: String,
    val numberQuestion: String = "",
    val time: Long,
    val icon: Int = 0,
    val type: QuestionType,
    val answers: List<AnswerItem>,
    val testId: String
)

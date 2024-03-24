package com.testeducation.local.entity.question

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnsweredQuestionEntity(
    @PrimaryKey
    val questionId: String,
    val testId: String,
    val answeredIds: List<String>,
    val answerState: String,
    val customAnswer: String?
)

package com.testeducation.local.entity.result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["questionId", "testId"])
data class AnsweredQuestionEntity(
    val questionId: String,
    val testId: String,
    val answeredIds: List<String>,
    val isCorrect: Boolean,
    val customAnswer: String?,
    val timeSpent: Long
)

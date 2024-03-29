package com.testeducation.local.entity.question

import androidx.room.Embedded
import androidx.room.Relation

data class AnsweredQuestionWithAnswers(
    @Embedded
    val answeredQuestion: AnsweredQuestionEntity,
    @Relation(QuestionEntity::class, parentColumn = "questionId", entityColumn = "id")
    val question: QuestionWithAnswers
)

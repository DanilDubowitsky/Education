package com.testeducation.local.entity.result

import androidx.room.Embedded
import androidx.room.Relation
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.question.QuestionWithAnswers

data class AnsweredQuestionWithAnswers(
    @Embedded
    val answeredQuestion: AnsweredQuestionEntity,
    @Relation(QuestionEntity::class, parentColumn = "questionId", entityColumn = "id")
    val question: QuestionWithAnswers
)

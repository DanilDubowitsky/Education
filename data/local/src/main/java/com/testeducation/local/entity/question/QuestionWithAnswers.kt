package com.testeducation.local.entity.question

import androidx.room.Embedded
import androidx.room.Relation
import com.testeducation.local.entity.answer.AnswerEntity

data class QuestionWithAnswers(
    @Embedded
    val question: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val answers: List<AnswerEntity>
)

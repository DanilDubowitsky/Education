package com.testeducation.local.entity.result

import androidx.room.Embedded
import androidx.room.Relation

data class TestPassResultCompound(
    @Embedded
    val passResultEntity: TestPassResultEntity,
    @Relation(
        entity = AnsweredQuestionEntity::class,
        parentColumn = "testId",
        entityColumn = "testId"
    )
    val userAnswers: List<AnsweredQuestionWithAnswers>
)

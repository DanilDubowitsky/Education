package com.testeducation.local.entity.answer

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.testeducation.local.entity.question.QuestionEntity

@Entity(
    foreignKeys = [ForeignKey(
        QuestionEntity::class,
        parentColumns = ["id"],
        childColumns = ["questionId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnswerEntity(
    @PrimaryKey
    val id: String,
    val questionId: String,
    val title: String,

    // Match answer
    val matchedCorrectText: String? = null,

    // Order answer
    val order: Int? = null,

    // Choice answer
    val isTrue: Boolean? = null,
)

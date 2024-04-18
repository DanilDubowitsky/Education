package com.testeducation.local.entity.answer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
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

    // Text
    val correctText: String? = null
)

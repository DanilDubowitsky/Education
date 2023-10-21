package com.testeducation.local.entity.question

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity
data class QuestionEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val numberQuestion: String,
    val time: Long,
    val type: String,
    val testId: String
)

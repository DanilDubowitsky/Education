package com.testeducation.local.entity.result

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TestPassResultEntity(
    @PrimaryKey
    val testId: String,
    val id: String? = null,
    val timeSpent: Long,
    val wasCheating: Boolean,
    val success: Boolean
)

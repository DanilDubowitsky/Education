package com.testeducation.local.entity.question

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestLocalLiveTimeEntity(
    @PrimaryKey
    val testId: String,
    val lastCacheTime: Long
)

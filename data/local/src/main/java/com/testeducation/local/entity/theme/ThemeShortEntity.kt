package com.testeducation.local.entity.theme

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ThemeShortEntity(
    @PrimaryKey
    val id: String,
    val title: String
)

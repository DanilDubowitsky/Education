package com.example.domain.model.test

import com.example.domain.model.theme.ThemeShort

data class TestShort(
    val id: String,
    val title: String,
    val questionsCount: Int,
    val isPublic: Boolean,
    val likes: Int,
    val passesCount: Int,
    val theme: ThemeShort
)

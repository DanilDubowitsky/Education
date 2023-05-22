package com.example.logic.model.test

import com.example.logic.model.theme.ThemeShortUI

data class TestShortUI(
    val id: String,
    val title: String,
    val questionsCount: Int,
    val isPublic: Boolean,
    val likes: Int,
    val passesCount: Int,
    val theme: ThemeShortUI
)

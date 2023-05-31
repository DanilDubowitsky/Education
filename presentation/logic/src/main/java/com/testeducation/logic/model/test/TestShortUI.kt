package com.testeducation.logic.model.test

import com.testeducation.logic.model.theme.ThemeShortUI

data class TestShortUI(
    val id: String,
    val title: String,
    val questionsCount: Int,
    val isPublic: Boolean,
    val likes: Int,
    val passesCount: Int,
    val theme: ThemeShortUI
)

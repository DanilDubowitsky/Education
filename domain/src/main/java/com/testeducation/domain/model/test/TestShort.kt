package com.testeducation.domain.model.test

import com.testeducation.domain.model.theme.ThemeShort

data class TestShort(
    val id: String,
    val title: String,
    val questionsCount: Int,
    val isPublic: Boolean,
    val likes: Int,
    val passesCount: Int,
    val theme: ThemeShort,
    val liked: Boolean,
    val passed: Boolean,
    val style: TestStyle
)

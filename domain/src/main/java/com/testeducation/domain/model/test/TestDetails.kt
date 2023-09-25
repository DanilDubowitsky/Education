package com.testeducation.domain.model.test

import com.testeducation.domain.model.question.QuestionItem
import com.testeducation.domain.model.theme.ThemeShort

data class TestDetails(
    val id: String,
    val title: String,
    val style: TestStyle,
    val settings: TestSettings,
    val questions: List<QuestionItem>,
    val status: String,
    val likes: Long,
    val liked: Boolean,
    val passesUser: Long,
    val passed: Boolean,
    val theme: ThemeShort,
)
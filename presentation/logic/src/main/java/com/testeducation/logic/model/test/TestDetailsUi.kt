package com.testeducation.logic.model.test

import com.testeducation.logic.model.question.QuestionItemUi
import com.testeducation.logic.model.theme.ThemeShortUI

data class TestDetailsUi(
    val id: String,
    val title: String,
    val style: TestStyleUi,
    val settings: TestShortUI.Test.Settings,
    val questions: List<QuestionItemUi>,
    val status: String,
    val likes: Long,
    val liked: Boolean,
    val passesUser: Long,
    val passed: Boolean,
    val theme: ThemeShortUI,
)
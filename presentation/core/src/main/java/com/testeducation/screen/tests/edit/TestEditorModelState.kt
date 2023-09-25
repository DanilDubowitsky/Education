package com.testeducation.screen.tests.edit

import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.test.TestDetails

data class TestEditorModelState(
    val testDetails: TestDetails? = null,
    val loading: Boolean = true,
    val questionDetails: List<QuestionDetails> = emptyList()
)
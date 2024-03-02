package com.testeducation.screen.tests.edit

import com.testeducation.domain.model.question.QuestionDetails
import com.testeducation.domain.model.test.Test

data class TestEditorModelState(
    val test: Test? = null,
    val loading: Boolean = true,
    val questionDetails: List<QuestionDetails> = emptyList(),
    val loadingPublish: Boolean = false
)
package com.testeducation.screen.tests.edit

import com.testeducation.domain.model.test.TestDetails

data class TestEditorModelState(
    val testDetails: TestDetails? = null,
    val loading: Boolean = true,
)
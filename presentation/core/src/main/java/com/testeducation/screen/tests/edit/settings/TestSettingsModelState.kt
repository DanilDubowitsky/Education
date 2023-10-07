package com.testeducation.screen.tests.edit.settings

import com.testeducation.domain.model.test.TestSettingsElement

data class TestSettingsModelState(
    val testElementList: List<TestSettingsElement> = emptyList()
)
package com.testeducation.screen.tests.edit.settings

import com.testeducation.domain.model.test.TestSettingsElement
import com.testeducation.domain.model.test.TestSettingsItem

data class TestSettingsModelState(
    val testElementList: List<TestSettingsElement> = emptyList(),
    val originalTestSettings: TestSettingsItem = TestSettingsItem(),
    val changedIdList: Set<Int> = emptySet(),
    val isLoading: Boolean = true
)
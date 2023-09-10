package com.testeducation.logic.screen.tests.liked

import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI

data class LikedTestsState(
    val tests: List<TestShortUI>,
    val themes: List<ThemeShortUI>,
    val isTestsLoading: Boolean,
    val isThemesLoading: Boolean,
    val selectedSortField: TestOrderFieldUI
)

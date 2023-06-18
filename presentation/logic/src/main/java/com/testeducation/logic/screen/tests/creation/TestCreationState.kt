package com.testeducation.logic.screen.tests.creation

import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.model.theme.ThemeShortUI

data class TestCreationState(
    val themes: List<ThemeShortUI>,
    val iconDesignList: List<IconDesignItem>,
    val isLoading: Boolean,
    val isFirstScreenVisible: Boolean,
)
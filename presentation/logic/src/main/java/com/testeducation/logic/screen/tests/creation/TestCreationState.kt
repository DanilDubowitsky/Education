package com.testeducation.logic.screen.tests.creation

import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI

data class TestCreationState(
    val themes: List<ThemeShortUI>,
    val iconDesignList: List<IconDesignItem>,
    val isLoading: Boolean,
    val isFirstScreenVisible: Boolean,
    val colorTestCard: String,
    // TODO: use another model for test creation
    val testShortUI: TestShortUI.Test,
    val btnCancelText: String,
    val btnNextText: String,
    val visibleProgressBar: Boolean
)

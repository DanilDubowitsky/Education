package com.testeducation.logic.screen.tests.settings

import com.testeducation.logic.model.test.IconDesignItem
import com.testeducation.logic.model.test.TestShortUI

data class TestStyleChangerState(
    val iconDesignList: List<IconDesignItem>,
    val colorTestCard: String,
    val testShortUI: TestShortUI.Test,
)
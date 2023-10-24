package com.testeducation.screen.tests.edit.style

import com.testeducation.logic.model.test.IconDesignItem

data class TestStyleChangerModelState(
    val iconDesign: List<IconDesignItem> = emptyList(),
)
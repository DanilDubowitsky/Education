package com.testeducation.screen.tests.sort

import com.testeducation.domain.model.global.OrderDirection
import com.testeducation.domain.model.test.TestOrderField

data class TestSortModelState(
    val selectedField: TestOrderField,
    val direction: OrderDirection
)

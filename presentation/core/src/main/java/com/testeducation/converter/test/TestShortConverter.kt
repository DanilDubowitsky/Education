package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestShort
import com.testeducation.logic.model.test.TestShortUI

fun TestShort.toUI() = TestShortUI(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toUI()
)

fun List<TestShort>.toUIModels() = this.map(TestShort::toUI)

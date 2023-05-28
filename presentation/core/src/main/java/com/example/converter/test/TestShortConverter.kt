package com.example.converter.test

import com.example.domain.model.test.TestShort
import com.example.logic.model.test.TestShortUI

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

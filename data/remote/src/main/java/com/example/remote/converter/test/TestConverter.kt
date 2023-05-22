package com.example.remote.converter.test

import com.example.domain.model.test.TestShort
import com.example.remote.model.test.RemoteTestShort

fun RemoteTestShort.toModel() = TestShort(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toModel()
)

fun List<RemoteTestShort>.toModels() = this.map(RemoteTestShort::toModel)

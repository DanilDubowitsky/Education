package com.testeducation.remote.converter.test

import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.model.test.RemoteTestShort

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

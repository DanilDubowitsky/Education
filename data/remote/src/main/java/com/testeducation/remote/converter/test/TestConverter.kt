package com.testeducation.remote.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.remote.model.test.RemoteTestShort

private const val TITLE = "title"
private const val CREATION = "creation"
private const val QUESTIONS = "questions"

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

fun TestOrderField.toRemote() = when (this) {
    TestOrderField.TITLE -> TITLE
    TestOrderField.CREATION -> CREATION
    TestOrderField.QUESTIONS -> QUESTIONS
}

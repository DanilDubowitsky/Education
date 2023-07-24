package com.testeducation.remote.converter.test

import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.remote.model.test.RemotePage
import com.testeducation.remote.model.test.RemoteTestSettings
import com.testeducation.remote.model.test.RemoteTestShort
import com.testeducation.remote.model.test.RemoteTestStyle
import java.security.InvalidParameterException

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
    theme.toModel(),
    liked,
    passed,
    style.toModel(),
    testSettings.toModel()
)

fun List<RemoteTestShort>.toModels() = this.map(RemoteTestShort::toModel)

fun TestOrderField.toRemote() = when (this) {
    TestOrderField.TITLE -> TITLE
    TestOrderField.CREATION -> CREATION
    TestOrderField.QUESTIONS -> QUESTIONS
}

fun RemotePage<RemoteTestShort>.toModel() = Page(
    pageIndex,
    pageSize,
    pageTotal,
    itemsTotal,
    tests.toModels()
)

private fun RemoteTestStyle.toModel() = TestStyle(
    color,
    background
)

private fun RemoteTestSettings.toModel() = TestSettings(
    availability.toTestAvailability(),
    previewQuestions
)

private fun String.toTestAvailability() = when (this) {
    AVAILABLE_STATUS_PUBLIC -> TestSettings.Availability.PUBLIC
    AVAILABLE_STATUS_PRIVATE -> TestSettings.Availability.PRIVATE
    else -> throw InvalidParameterException("$this is invalid")
}

private const val AVAILABLE_STATUS_PUBLIC = "Public"
private const val AVAILABLE_STATUS_PRIVATE = "Private"

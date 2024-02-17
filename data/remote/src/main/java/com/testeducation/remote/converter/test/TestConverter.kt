package com.testeducation.remote.converter.test

import com.testeducation.domain.model.test.Page
import com.testeducation.domain.model.test.Test
import com.testeducation.domain.model.test.TestCreationShort
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.remote.converter.user.toModel
import com.testeducation.remote.model.test.RemoteCreationTest
import com.testeducation.remote.model.test.RemotePage
import com.testeducation.remote.model.test.RemoteTest
import com.testeducation.remote.model.test.RemoteTestSettings
import com.testeducation.remote.model.test.RemoteTestShort
import com.testeducation.remote.model.test.RemoteTestStyle
import java.security.InvalidParameterException

private const val TITLE = "title"
private const val CREATION = "creation"
private const val QUESTIONS = "questions"

private const val DRAFT = "draft"
private const val SCHEDULED = "scheduled"
private const val PUBLISHED = "published"

fun Test.Status.toRemote() = when (this) {
    Test.Status.DRAFT -> DRAFT
    Test.Status.SCHEDULED -> SCHEDULED
    Test.Status.PUBLISHED -> PUBLISHED
    Test.Status.LOCKED -> TODO("ADD LOCKED SUPPORT")
}

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
    style.toModel()
)

fun List<RemoteTestShort>.toModels() = this.map(RemoteTestShort::toModel)

fun RemoteCreationTest.toModel() = TestCreationShort(
    id, title, testStyle
)

fun TestOrderField.toRemote() = when (this) {
    TestOrderField.TITLE -> TITLE
    TestOrderField.CREATION -> CREATION
    TestOrderField.QUESTIONS -> QUESTIONS
    TestOrderField.PUBLISHED -> PUBLISHED
}

fun RemotePage<RemoteTestShort>.toModel() = Page(
    pageIndex,
    pageSize,
    pageTotal,
    itemsTotal,
    tests.toModels()
)

fun RemoteTest.toModel(): Test {
    return Test(
        id = id,
        title = title,
        style = style.toModel(),
        settings = settings.toModel(),
        status = status.toModel(),
        likes = likes,
        liked = liked,
        passesUser = passesUser,
        passed = passed,
        theme = theme.toModel(),
        creationDate = creationDate,
        creator = creator.toModel(),
        questionsCount = questionsCount
    )
}

private fun RemoteTest.Status.toModel() = when (this) {
    RemoteTest.Status.Draft -> Test.Status.DRAFT
    RemoteTest.Status.Scheduled -> Test.Status.SCHEDULED
    RemoteTest.Status.Published -> Test.Status.PUBLISHED
    RemoteTest.Status.Locked -> Test.Status.LOCKED
}

private fun RemoteTestStyle.toModel() = TestStyle(
    color,
    background
)

private fun RemoteTestSettings.toModel() = TestSettings(
    availability.toTestAvailability(),
    previewQuestions,
    minCorrectAnswers,
    antiCheating,
    timeLimit
)

private fun String.toTestAvailability() = when (this) {
    AVAILABLE_STATUS_PUBLIC -> TestSettings.Availability.PUBLIC
    AVAILABLE_STATUS_PRIVATE -> TestSettings.Availability.ViaLinkAll
    else -> throw InvalidParameterException("$this is invalid")
}

private const val AVAILABLE_STATUS_PUBLIC = "Public"
private const val AVAILABLE_STATUS_PRIVATE = "ViaLinkAll"

package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI

fun TestShort.toUI(): TestShortUI {
    val cardStyle = if (style.background.isNotBlank()) {
        CardTestStyle.valueOf(style.background)
    } else {
        CardTestStyle.X
    }
    return TestShortUI.Test(
        id,
        title,
        questionsCount,
        isPublic,
        likes,
        passesCount,
        theme.toUI(),
        style.color,
        cardStyle,
        liked,
        passed
    )
}

fun List<TestShort>.toUIModels(): List<TestShortUI> = this.map(TestShort::toUI)

fun TestOrderField.toUIModel() = when (this) {
    TestOrderField.TITLE -> TestOrderFieldUI.TITLE
    TestOrderField.QUESTIONS -> TestOrderFieldUI.QUESTIONS
    TestOrderField.PUBLISHED -> TestOrderFieldUI.PUBLISHED
}

fun TestOrderFieldUI.toModel() = when (this) {
    TestOrderFieldUI.TITLE -> TestOrderField.TITLE
    TestOrderFieldUI.QUESTIONS -> TestOrderField.QUESTIONS
    TestOrderFieldUI.PUBLISHED -> TestOrderField.PUBLISHED
}

fun TestShortUI.Test.toModel() = TestShort(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toModel(),
    liked,
    passed,
    TestStyle(color, style.name)
)

fun List<TestShortUI>.toModels() = this.mapNotNull { testShortUI ->
    if (testShortUI is TestShortUI.Test) testShortUI.toModel()
    else null
}

fun TestSettings.toUI() = TestShortUI.Test.Settings(
    availability.toUI(),
    previewQuestions,
    minCorrectAnswers,
    antiCheating,
    timeLimit,
    questionsOrder.name
)

private fun TestShortUI.Test.Settings.toModel() = TestSettings(
    availability.toModel(),
    previewQuestions,
    minCorrectAnswers,
    antiCheating,
    timeLimit,
    TestSettings.QuestionsOrder.valueOf(questionsOrder)
)

private fun TestSettings.Availability.toUI() = when (this) {
    TestSettings.Availability.PUBLIC -> TestShortUI.Test.Settings.Availability.PUBLIC
    TestSettings.Availability.ViaLinkAll -> TestShortUI.Test.Settings.Availability.PRIVATE
}

private fun TestShortUI.Test.Settings.Availability.toModel() = when (this) {
    TestShortUI.Test.Settings.Availability.PUBLIC -> TestSettings.Availability.PUBLIC
    TestShortUI.Test.Settings.Availability.PRIVATE -> TestSettings.Availability.ViaLinkAll
}

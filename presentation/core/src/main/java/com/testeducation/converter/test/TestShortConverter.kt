package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestGetType
import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestGetTypeUI
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI

fun TestShort.toUI(): TestShortUI = TestShortUI.Test(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toUI(),
    style.color,
    CardTestStyle.ELLIPSE,
    liked,
    passed
)

fun List<TestShort>.toUIModels(): List<TestShortUI> = this.map(TestShort::toUI)

fun TestOrderField.toUIModel() = when (this) {
    TestOrderField.TITLE -> TestOrderFieldUI.TITLE
    TestOrderField.CREATION -> TestOrderFieldUI.CREATION
    TestOrderField.QUESTIONS -> TestOrderFieldUI.QUESTIONS
}

fun TestOrderFieldUI.toModel() = when (this) {
    TestOrderFieldUI.TITLE -> TestOrderField.TITLE
    TestOrderFieldUI.CREATION -> TestOrderField.CREATION
    TestOrderFieldUI.QUESTIONS -> TestOrderField.QUESTIONS
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
    TestStyle(color, "")
)

fun List<TestShortUI>.toModels() = this.mapNotNull { testShortUI ->
    if (testShortUI is TestShortUI.Test) testShortUI.toModel()
    else null
}

fun TestGetType.toUI() = when (this) {
    TestGetType.MAIN -> TestGetTypeUI.MAIN
    TestGetType.LIKED -> TestGetTypeUI.LIKED
    TestGetType.CREATED -> TestGetTypeUI.CREATED
    TestGetType.PASSED -> TestGetTypeUI.PASSED
}

fun TestGetTypeUI.toModel() = when (this) {
    TestGetTypeUI.MAIN -> TestGetType.MAIN
    TestGetTypeUI.LIKED -> TestGetType.LIKED
    TestGetTypeUI.CREATED -> TestGetType.CREATED
    TestGetTypeUI.PASSED -> TestGetType.PASSED
}

fun TestSettings.toUI() = TestShortUI.Test.Settings(
    availability.toUI(),
    previewQuestions,
    minCorrectAnswers,
    antiCheating,
    timeLimit
)

private fun TestShortUI.Test.Settings.toModel() = TestSettings(
    availability.toModel(),
    previewQuestions,
    minCorrectAnswers,
    antiCheating,
    timeLimit
)

private fun TestSettings.Availability.toUI() = when (this) {
    TestSettings.Availability.PUBLIC -> TestShortUI.Test.Settings.Availability.PUBLIC
    TestSettings.Availability.PRIVATE -> TestShortUI.Test.Settings.Availability.PRIVATE
}

private fun TestShortUI.Test.Settings.Availability.toModel() = when (this) {
    TestShortUI.Test.Settings.Availability.PUBLIC -> TestSettings.Availability.PUBLIC
    TestShortUI.Test.Settings.Availability.PRIVATE -> TestSettings.Availability.PRIVATE
}

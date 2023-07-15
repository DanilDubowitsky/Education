package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestSettings
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.test.TestStyle
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.utils.MainColor.tempColors
import kotlin.random.Random
import kotlin.random.nextInt

fun TestShort.toUI() = TestShortUI(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toUI(),
    style.color,
    getStyle(),
    liked,
    passed,
    settings.toUI()
)

fun List<TestShort>.toUIModels() = this.map(TestShort::toUI)

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

fun TestShortUI.toModel() = TestShort(
    id,
    title,
    questionsCount,
    isPublic,
    likes,
    passesCount,
    theme.toModel(),
    liked,
    passed,
    TestStyle(color, ""),
    settings.toModel()
)

fun List<TestShortUI>.toModels() = this.map(TestShortUI::toModel)

private fun TestSettings.toUI() = TestShortUI.Settings(
    availability.toUI(),
    previewQuestions
)

private fun TestShortUI.Settings.toModel() = TestSettings(
    availability.toModel(),
    previewQuestions
)

private fun TestSettings.Availability.toUI() = when (this) {
    TestSettings.Availability.PUBLIC -> TestShortUI.Settings.Availability.PUBLIC
    TestSettings.Availability.PRIVATE -> TestShortUI.Settings.Availability.PRIVATE
}

private fun TestShortUI.Settings.Availability.toModel() = when (this) {
    TestShortUI.Settings.Availability.PUBLIC -> TestSettings.Availability.PUBLIC
    TestShortUI.Settings.Availability.PRIVATE -> TestSettings.Availability.PRIVATE
}

// TODO: remove this when styles and colors added on back end
private fun getColor(): String {
    val rand = Random.nextInt(0..3)
    return tempColors[rand]
}

private fun getStyle(): CardTestStyle {
    val rand = Random.nextInt(0..3)
    return CardTestStyle.values()[rand]
}

package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI
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
    getColor(),
    getStyle()
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

// TODO: remove this when styles and colors added on back end
private fun getColor(): String {
    val rand = Random.nextInt(0..3)
    return tempColors[rand]
}

private fun getStyle(): TestShortUI.Style {
    val rand = Random.nextInt(0..3)
    return TestShortUI.Style.values()[rand]
}

private val tempColors = arrayOf("#FCAF2A", "#FF6951", "#4B9FFF", "#1CCD9D")

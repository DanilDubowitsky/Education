package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestOrderFieldUI
import com.testeducation.logic.model.test.TestShortUI
import com.testeducation.logic.model.theme.ThemeShortUI
import com.testeducation.utils.MainColor
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
    getColor(),
    getStyle()
)

fun createTestShortUI(title: String = "", themeShort: ThemeShort, color: String, style: CardTestStyle) = TestShortUI(
    id = "",
    title = title,
    questionsCount = 0,
    isPublic = false,
    likes = 0,
    passesCount = 0,
    theme = themeShort.toUISelected(),
    color = color,
    style = style
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

private fun getStyle(): CardTestStyle {
    val rand = Random.nextInt(0..3)
    return CardTestStyle.values()[rand]
}

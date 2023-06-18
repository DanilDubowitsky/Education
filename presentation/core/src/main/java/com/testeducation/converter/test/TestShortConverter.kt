package com.testeducation.converter.test

import com.testeducation.domain.model.test.TestOrderField
import com.testeducation.domain.model.test.TestShort
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
    getColor(),
    getStyle()
)

fun List<TestShort>.toUIModels() = this.map(TestShort::toUI)

fun TestOrderField.toUIModel() = when (this) {
    TestOrderField.TITLE -> TestOrderFieldUI.TITLE
    TestOrderField.CREATION -> TestOrderFieldUI.CREATION
    TestOrderField.QUESTIONS -> TestOrderFieldUI.QUESTIONS
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

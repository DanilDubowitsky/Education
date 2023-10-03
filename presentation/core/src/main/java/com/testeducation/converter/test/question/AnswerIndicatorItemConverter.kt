package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.AnswerIndicatorItem
import com.testeducation.logic.model.test.AnswerIndicatorItemUi

fun List<AnswerIndicatorItem>.toListUi() = map {
    AnswerIndicatorItemUi(
        text = it.text, color = it.color
    )
}
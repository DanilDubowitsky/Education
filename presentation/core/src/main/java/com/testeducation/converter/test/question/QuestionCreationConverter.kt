package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.AnswerItem
import com.testeducation.logic.model.test.AnswerItemUi

fun List<AnswerItem>.toModelUi() = this.map { answerItem ->
    when (answerItem) {
        is AnswerItem.DefaultAnswer -> {
            AnswerItemUi.DefaultAnswer(
                id = answerItem.id,
                answerText = answerItem.answerText,
                isTrue = answerItem.isTrue,
                isUrl = answerItem.isUrl,
                color = answerItem.color
            )
        }

        is AnswerItem.TextAnswer -> {
            AnswerItemUi.TextAnswer(
                id = answerItem.id
            )
        }

        is AnswerItem.MatchAnswer -> {
            AnswerItemUi.MatchAnswer(
                id = answerItem.id
            )
        }

        is AnswerItem.FooterPlusAdd -> {
            AnswerItemUi.FooterPlusAdd(
                id = answerItem.id
            )
        }
    }
}
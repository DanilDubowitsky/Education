package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.QuestionType
import com.testeducation.domain.model.question.QuestionTypeItem
import com.testeducation.logic.model.test.QuestionTypeUi
import com.testeducation.logic.model.test.QuestionTypeUiItem

fun List<QuestionTypeItem>.toUiModel() = this.map {
    when (it.questionType) {
        QuestionType.MATCH -> {
            QuestionTypeUi.MATCH
        }

        QuestionType.ACCORD -> {
            QuestionTypeUi.ACCORD
        }

        QuestionType.WRITE_ANSWER -> {
            QuestionTypeUi.WRITE_ANSWER
        }
    }.let { typeUi ->
        QuestionTypeUiItem(
            type = typeUi
        )
    }
}
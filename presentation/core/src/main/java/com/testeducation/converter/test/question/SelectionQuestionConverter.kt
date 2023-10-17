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

        QuestionType.CHOICE -> {
            QuestionTypeUi.DEFAULT
        }

        QuestionType.TEXT -> {
            QuestionTypeUi.WRITE_ANSWER
        }

        QuestionType.REORDER -> {
            QuestionTypeUi.ORDER
        }
    }.let { typeUi ->
        QuestionTypeUiItem(
            type = typeUi
        )
    }
}

fun QuestionTypeUiItem.toModel() = when (this.type) {
    QuestionTypeUi.MATCH -> {
        QuestionType.MATCH
    }
    QuestionTypeUi.DEFAULT -> {
        QuestionType.CHOICE
    }
    QuestionTypeUi.WRITE_ANSWER -> {
        QuestionType.TEXT
    }
    QuestionTypeUi.ORDER -> {
        QuestionType.REORDER
    }
}.let {
    QuestionTypeItem(it)
}

fun QuestionType.toUiModel() = when (this) {
    QuestionType.MATCH -> {
        QuestionTypeUi.MATCH
    }
    QuestionType.CHOICE -> {
        QuestionTypeUi.DEFAULT
    }
    QuestionType.TEXT -> {
        QuestionTypeUi.WRITE_ANSWER
    }
    QuestionType.REORDER -> {
        QuestionTypeUi.ORDER
    }
}.let {
    QuestionTypeUiItem(it)
}
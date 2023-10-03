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

        QuestionType.DEFAULT -> {
            QuestionTypeUi.DEFAULT
        }

        QuestionType.WRITE_ANSWER -> {
            QuestionTypeUi.WRITE_ANSWER
        }

        QuestionType.ORDER -> {
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
        QuestionType.DEFAULT
    }
    QuestionTypeUi.WRITE_ANSWER -> {
        QuestionType.WRITE_ANSWER
    }
    QuestionTypeUi.ORDER -> {
        QuestionType.ORDER
    }
}.let {
    QuestionTypeItem(it)
}

fun QuestionType.toUiModel() = when (this) {
    QuestionType.MATCH -> {
        QuestionTypeUi.MATCH
    }
    QuestionType.DEFAULT -> {
        QuestionTypeUi.DEFAULT
    }
    QuestionType.WRITE_ANSWER -> {
        QuestionTypeUi.WRITE_ANSWER
    }
    QuestionType.ORDER -> {
        QuestionTypeUi.ORDER
    }
}.let {
    QuestionTypeUiItem(it)
}
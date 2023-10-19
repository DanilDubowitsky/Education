package com.testeducation.converter.test.question

import com.testeducation.domain.model.question.Answer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.logic.model.test.AnswerUI

fun List<Answer>.toUIModels(
    answerColorExtractor: IAnswerColorExtractor
): List<AnswerUI> {
    val colors = answerColorExtractor.extractAnswersColors()
    return mapIndexed { index, answer ->
        val color = colors[index]
        answer.toUI(color)
    }
}

fun Answer.toUI(color: Int) = when (this) {
    is Answer.ChoiceAnswer -> {
        AnswerUI.ChoiceAnswer(
            id = id,
            title = title,
            isTrue = isTrue,
            color = color
        )
    }

    is Answer.TextAnswer -> {
        AnswerUI.TextAnswer(
            id = id,
            color = color
        )
    }

    is Answer.MatchAnswer -> {
        AnswerUI.MatchAnswer(
            id = id,
            matchedCorrectText = matchedCorrectText,
            color = color
        )
    }

    is Answer.OrderAnswer -> {
        AnswerUI.OrderAnswer(
            id = id,
            title = title,
            order = order,
            color = color
        )
    }
}
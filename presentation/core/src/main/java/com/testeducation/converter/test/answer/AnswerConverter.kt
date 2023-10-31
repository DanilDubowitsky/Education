package com.testeducation.converter.test.answer

import com.testeducation.domain.model.answer.Answer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.screen.tests.pass.TestPassingModelState

fun List<Answer>.toUIModels(
    answerColorExtractor: IAnswerColorExtractor,
    state: TestPassingModelState.PassingQuestion.AnswerState,
    selectedId: String? = null
): List<AnswerUI> {
    val simpleColors = answerColorExtractor.extractAnswersColors()
    val alphaColors = answerColorExtractor.extractAnswersColors(withAlpha = true)
    return mapIndexed { index, answer ->
        val color = if (answer is Answer.ChoiceAnswer &&
            state != TestPassingModelState.PassingQuestion.AnswerState.NONE
        ) {
            if (answer.isTrue) simpleColors[index]
            else alphaColors[index]
        } else {
            simpleColors[index]
        }
        answer.toUI(color, selectedId)
    }
}

fun Answer.toUI(color: Int, selectedId: String?) = when (this) {
    is Answer.ChoiceAnswer -> {
        AnswerUI.ChoiceAnswer(
            id = id,
            title = title,
            isTrue = isTrue,
            color = color,
            isSelected = selectedId == id
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
            title = title,
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
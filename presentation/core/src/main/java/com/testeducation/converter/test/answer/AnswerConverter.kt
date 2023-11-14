package com.testeducation.converter.test.answer

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.input.InputAnswer
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.logic.model.test.AnswerUI
import com.testeducation.screen.tests.pass.TestPassingModelState

fun List<Answer>.toUIModels(
    answerColorExtractor: IAnswerColorExtractor,
    state: PassingQuestion.AnswerState,
    selectedId: String? = null
): List<AnswerUI> {
    val simpleColors = answerColorExtractor.extractAnswersColors()
    val alphaColors = answerColorExtractor.extractAnswersColors(withAlpha = true)
    return mapIndexed { index, answer ->
        val color = if (answer is Answer.ChoiceAnswer &&
            state != PassingQuestion.AnswerState.NONE
        ) {
            if (answer.isTrue) simpleColors[index]
            else alphaColors[index]
        } else {
            simpleColors[index]
        }
        answer.toUI(color, selectedId)
    }
}

fun List<Answer>.toSimpleUIModels() = map { answer ->
    answer.toUI(0, null)
}

fun List<Answer>.toInputAnswers(getColor: ((Int) -> Int)? = null, getTrueColor: ((Boolean) -> Int)? = null) =
    mapIndexed { index, itemAnswer ->
        when (itemAnswer) {
            is Answer.ChoiceAnswer -> {
                InputAnswer.DefaultAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    isTrue = itemAnswer.isTrue,
                    color = getColor?.invoke(index) ?: 0,
                    resource = InputAnswer.DefaultAnswer.Resource(
                        isTrueColor = getTrueColor?.invoke(itemAnswer.isTrue) ?: 0
                    )
                )
            }

            is Answer.MatchAnswer -> {
                InputAnswer.MatchAnswer(
                    id = itemAnswer.id,
                    firstAnswer = itemAnswer.title,
                    secondAnswer = itemAnswer.matchedCorrectText,
                    color = getColor?.invoke(index) ?: 0
                )
            }

            is Answer.OrderAnswer -> {
                InputAnswer.OrderAnswer(
                    id = itemAnswer.id,
                    answerText = itemAnswer.title,
                    order = itemAnswer.order,
                    color = getColor?.invoke(index) ?: 0
                )
            }

            is Answer.TextAnswer -> {
                InputAnswer.TextAnswer(
                    id = itemAnswer.id,
                    text = itemAnswer.questionId
                )
            }
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
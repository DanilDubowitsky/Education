package com.testeducation.helper.answer

import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question

fun Question.toPassingQuestion(
    state: PassingQuestion.AnswerState =
        PassingQuestion.AnswerState.NONE,
    spentTime: Long = 0L,
    answers: List<String> = emptyList(),
    isInitial: Boolean = false
) = PassingQuestion(
    question = if (isInitial) {
        this.randomAnswers()
    } else {
        this
    },
    state = state,
    answers = answers,
    timeSpent = spentTime,
    customAnswer = ""
)

fun List<Question>.toPassingQuestions(isInitial: Boolean = false) = map { question ->
    question.toPassingQuestion(isInitial = isInitial)
}

private fun Question.randomAnswers() = when (this) {
    is Question.Choice -> this.copy(answers = answers.shuffled())
    is Question.Match -> this.copy(answers = answers.shuffled())
    is Question.Order -> this.copy(answers = answers.shuffled())
    is Question.Text -> this
}

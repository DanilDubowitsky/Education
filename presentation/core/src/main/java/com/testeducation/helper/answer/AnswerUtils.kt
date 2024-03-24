package com.testeducation.helper.answer

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question

fun Question.toPassingQuestion(
    state: PassingQuestion.AnswerState =
        PassingQuestion.AnswerState.NONE,
    spentTime: Long = 0L,
    answers: List<String> = emptyList(),
    isInitial: Boolean = false
): PassingQuestion {
    val matchData = if (isInitial && this is Question.Match) {
        this.answers.map(Answer.MatchAnswer::matchedCorrectText)
    } else emptyList()

    return PassingQuestion(
        question = if (isInitial) {
            this.randomAnswers()
        } else {
            this
        },
        state = state,
        answers = answers,
        timeSpent = spentTime,
        customAnswer = "",
        matchData = matchData
    )
}

fun List<Question>.toPassingQuestions(isInitial: Boolean = false) = map { question ->
    question.toPassingQuestion(isInitial = isInitial)
}

private fun Question.randomAnswers() = when (this) {
    is Question.Choice -> this.copy(answers = answers.shuffled())
    is Question.Match -> this.copy(answers = answers.shuffled())
    is Question.Order -> this.copy(answers = answers.shuffled())
    is Question.Text -> this
}

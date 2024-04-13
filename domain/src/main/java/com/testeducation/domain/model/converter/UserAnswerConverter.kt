package com.testeducation.domain.model.converter

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.result.UserAnswer

fun List<UserAnswer>.toAnsweredQuestions() = this.map(UserAnswer::toAnsweredQuestion)

private fun UserAnswer.toAnsweredQuestion(): AnsweredQuestion {
    return when (question) {
        is Question.Choice -> {
            val chosenAnswer: ArrayList<Answer.ChoiceAnswer> = ArrayList()
            question.answers.forEach { choiceAnswer ->
                if (answers.contains(choiceAnswer.id)) {
                    chosenAnswer.add(choiceAnswer)
                }
            }
            val correctAnswer = question.answers.filter(Answer.ChoiceAnswer::isTrue)
            AnsweredQuestion.Choose(
                question.id,
                question.title,
                isCorrect,
                question.numberQuestion,
                chosenAnswer,
                correctAnswer,
            )
        }

        is Question.Match -> {
            val matchedAnswers = answers.map { id ->
                question.answers.first {
                    it.id == id
                }
            }
            val matchData = question.answers.map(Answer.MatchAnswer::matchedCorrectText)

            AnsweredQuestion.Match(
                question.id,
                question.title,
                isCorrect,
                question.numberQuestion,
                matchData,
                matchedAnswers,
                question.answers
            )
        }

        is Question.Order -> {
            val correctOrder = question.answers.sortedBy(Answer.OrderAnswer::order)
            val answeredOrder = answers.map { id ->
                question.answers.first {
                    it.id == id
                }
            }

            AnsweredQuestion.Order(
                question.id,
                question.title,
                isCorrect,
                question.numberQuestion,
                correctOrder,
                answeredOrder
            )
        }

        is Question.Text -> {
            AnsweredQuestion.Text(
                question.id,
                question.title,
                isCorrect,
                question.numberQuestion,
                customAnswer.orEmpty(),
                answer = question.answers.first()
            )
        }
    }
}

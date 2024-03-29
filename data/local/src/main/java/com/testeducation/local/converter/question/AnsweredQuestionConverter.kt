package com.testeducation.local.converter.question

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.PassingQuestion
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.answered.AnsweredQuestion
import com.testeducation.domain.model.question.input.InputUserAnswerData
import com.testeducation.local.converter.toEnumEntity
import com.testeducation.local.converter.toEnumModel
import com.testeducation.local.entity.question.AnsweredQuestionEntity
import com.testeducation.local.entity.question.AnsweredQuestionWithAnswers

fun List<InputUserAnswerData>.toEntities(testId: String) = map { question ->
    question.toEntity(testId)
}

fun InputUserAnswerData.toEntity(testId: String): AnsweredQuestionEntity {
    val state = when (isCorrect) {
        true -> PassingQuestion.AnswerState.CORRECT
        false -> PassingQuestion.AnswerState.INCORRECT
        null -> PassingQuestion.AnswerState.NONE
    }
    return AnsweredQuestionEntity(
        questionId,
        testId,
        answerIds,
        state.toEnumEntity(),
        customAnswer
    )
}

fun AnsweredQuestionWithAnswers.toModel(): AnsweredQuestion {
    return when (val domainQuestion = question.toModel()) {
        is Question.Choice -> {
            val chosenAnswer: ArrayList<Answer.ChoiceAnswer> = ArrayList()
            domainQuestion.answers.forEach { choiceAnswer ->
                if (answeredQuestion.answeredIds.contains(choiceAnswer.id)) {
                    chosenAnswer.add(choiceAnswer)
                }
            }
            val correctAnswer = domainQuestion.answers.filter(Answer.ChoiceAnswer::isTrue)
            AnsweredQuestion.Choose(
                domainQuestion.id,
                domainQuestion.title,
                answeredQuestion.answerState.toEnumModel(),
                question.question.numberQuestion,
                chosenAnswer,
                correctAnswer,
            )
        }

        is Question.Match -> {
            val matchedAnswers = answeredQuestion.answeredIds.map { id ->
                domainQuestion.answers.first {
                    it.id == id
                }
            }
            val matchData = domainQuestion.answers.map(Answer.MatchAnswer::matchedCorrectText)

            AnsweredQuestion.Match(
                domainQuestion.id,
                domainQuestion.title,
                answeredQuestion.answerState.toEnumModel(),
                question.question.numberQuestion,
                matchData,
                matchedAnswers,
                domainQuestion.answers
            )
        }

        is Question.Order -> {
            val correctOrder = domainQuestion.answers.sortedBy(Answer.OrderAnswer::order)
            val answeredOrder = answeredQuestion.answeredIds.map { id ->
                domainQuestion.answers.first {
                    it.id == id
                }
            }

            AnsweredQuestion.Order(
                domainQuestion.id,
                domainQuestion.title,
                answeredQuestion.answerState.toEnumModel(),
                question.question.numberQuestion,
                correctOrder,
                answeredOrder
            )
        }

        is Question.Text -> {
            AnsweredQuestion.Text(
                domainQuestion.id,
                domainQuestion.title,
                answeredQuestion.answerState.toEnumModel(),
                question.question.numberQuestion,
                answeredQuestion.customAnswer.orEmpty(),
                answer = domainQuestion.answers.first()
            )
        }
    }
}

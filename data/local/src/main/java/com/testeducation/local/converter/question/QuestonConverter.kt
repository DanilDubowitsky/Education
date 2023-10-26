package com.testeducation.local.converter.question

import com.testeducation.domain.model.answer.Answer
import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.question.QuestionType
import com.testeducation.local.converter.answer.toEntities
import com.testeducation.local.converter.answer.toModels
import com.testeducation.local.converter.toEnumEntity
import com.testeducation.local.converter.toEnumModel
import com.testeducation.local.entity.answer.AnswerEntity
import com.testeducation.local.entity.question.QuestionEntity
import com.testeducation.local.entity.question.QuestionWithAnswers

fun List<QuestionWithAnswers>.toModels() = map(QuestionWithAnswers::toModel)

fun QuestionWithAnswers.toModel() =
    question.toModel(answers)

fun QuestionEntity.toModel(answers: List<AnswerEntity>): Question {
    return when (val enumType = type.toEnumModel<QuestionType>()) {
        QuestionType.MATCH -> Question.Match(
            id,
            title,
            numberQuestion,
            time,
            answers.toModels(enumType) as List<Answer.MatchAnswer>
        )

        QuestionType.CHOICE -> Question.Choice(
            id,
            title,
            numberQuestion,
            time,
            answers.toModels(enumType) as List<Answer.ChoiceAnswer>
        )

        QuestionType.TEXT -> Question.Text(
            id,
            title,
            numberQuestion,
            time
        )

        QuestionType.REORDER -> Question.Order(
            id,
            title,
            numberQuestion,
            time,
            answers.toModels(enumType) as List<Answer.OrderAnswer>
        )
    }
}

fun Question.toEntity(testId: String) = when (this) {
    is Question.Choice -> QuestionEntity(
        id,
        title,
        numberQuestion,
        time,
        QuestionType.CHOICE.toEnumEntity(),
        testId
    )

    is Question.Match -> QuestionEntity(
        id,
        title,
        numberQuestion,
        time,
        QuestionType.MATCH.toEnumEntity(),
        testId
    )

    is Question.Order -> QuestionEntity(
        id,
        title,
        numberQuestion,
        time,
        QuestionType.REORDER.toEnumEntity(),
        testId
    )

    is Question.Text -> QuestionEntity(
        id,
        title,
        numberQuestion,
        time,
        QuestionType.TEXT.toEnumEntity(),
        testId
    )
}

fun List<Question>.toEntities(testId: String) = map { question ->
    question.toEntity(testId)
}

fun List<Question>.toQuestionsWithAnswers(testId: String) = map { question ->
    question.toQuestionWithAnswers(testId)
}

fun Question.toQuestionWithAnswers(testId: String): QuestionWithAnswers {
    val answers = when (this) {
        is Question.Choice -> answers.toEntities(id)
        is Question.Match -> answers.toEntities(id)
        is Question.Order -> answers.toEntities(id)
        is Question.Text -> emptyList()
    }
    return QuestionWithAnswers(
        this.toEntity(testId),
        answers
    )
}

package com.testeducation.domain.cases.question

import com.testeducation.domain.repository.question.IAnsweredQuestionRepository

class GetTestPassStatistic(
    private val repository: IAnsweredQuestionRepository
) {

    suspend operator fun invoke(testId: String) = repository.getAnsweredQuestions(testId)
}

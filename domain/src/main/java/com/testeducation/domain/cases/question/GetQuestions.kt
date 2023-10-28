package com.testeducation.domain.cases.question

import com.testeducation.domain.repository.question.IQuestionRepository

class GetQuestions(
    private val questionRepository: IQuestionRepository
) {

    suspend operator fun invoke(testId: String) =
        questionRepository.getQuestions(testId)
}

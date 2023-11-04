package com.testeducation.education.di.modules.domain.usecase.question

import com.testeducation.domain.cases.question.DeleteQuestion
import com.testeducation.domain.cases.question.GetQuestionDetails
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.question.QuestionCreate
import com.testeducation.domain.repository.question.IQuestionRepository
import com.testeducation.domain.service.question.IQuestionService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object QuestionUseCaseModule {

    @Provides
    @Reusable
    fun provideQuestionCreate(
        questionService: IQuestionService
    ): QuestionCreate = QuestionCreate(questionService)

    @Provides
    @Reusable
    fun provideGetQuestions(questionRepository: IQuestionRepository): GetQuestions =
        GetQuestions(questionRepository)

    @Provides
    @Reusable
    fun provideDeleteQuestion(questionRepository: IQuestionService) =
        DeleteQuestion(questionRepository)

    @Provides
    @Reusable
    fun provideGetQuestionDetails(questionRepository: IQuestionService) =
        GetQuestionDetails(questionRepository)
}

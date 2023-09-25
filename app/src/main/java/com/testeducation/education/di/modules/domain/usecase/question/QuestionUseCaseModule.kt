package com.testeducation.education.di.modules.domain.usecase.question

import com.testeducation.domain.cases.question.QuestionCreate
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
    ) : QuestionCreate = QuestionCreate(questionService)
}
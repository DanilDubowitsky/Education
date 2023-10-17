package com.testeducation.education.di.modules.core.repository.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.core.repository.question.QuestionRepository
import com.testeducation.core.service.question.QuestionService
import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.domain.repository.question.IQuestionRepository
import com.testeducation.domain.service.question.IQuestionService
import com.testeducation.remote.client.remote.question.QuestionRemoteClient
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object QuestionCoreModule {

    @Provides
    @Reusable
    fun provideQuestionServiceClient(
        questionRemoteClient: IQuestionRemoteClient
    ): IQuestionService = QuestionService(questionRemoteClient)

    @Provides
    @Reusable
    fun provideQuestionRemoteClient(
        questionRetrofitClient: QuestionRetrofitClient
    ) : IQuestionRemoteClient = QuestionRemoteClient(questionRetrofitClient)

    @Provides
    @Reusable
    fun provideQuestionRepository(
        questionRemoteSource: IQuestionRemoteSource
    ): IQuestionRepository = QuestionRepository(questionRemoteSource)
}
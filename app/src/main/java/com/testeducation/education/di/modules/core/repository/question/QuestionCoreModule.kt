package com.testeducation.education.di.modules.core.repository.question

import com.testeducation.core.client.remote.question.IQuestionRemoteClient
import com.testeducation.core.service.question.QuestionService
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
}
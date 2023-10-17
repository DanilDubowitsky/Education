package com.testeducation.education.di.modules.remote.question

import com.testeducation.core.source.remote.question.IQuestionRemoteSource
import com.testeducation.remote.client.retrofit.question.QuestionRetrofitClient
import com.testeducation.remote.source.question.QuestionRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object QuestionRemoteModule {

    @Provides
    @Reusable
    fun provideQuestionRemoteSource(
        questionRemoteClient: QuestionRetrofitClient
    ): IQuestionRemoteSource = QuestionRemoteSource(questionRemoteClient)

}

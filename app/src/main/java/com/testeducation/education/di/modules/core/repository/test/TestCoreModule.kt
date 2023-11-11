package com.testeducation.education.di.modules.core.repository.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.core.repository.test.TestRepository
import com.testeducation.core.service.test.TestService
import com.testeducation.core.source.local.question.IAnsweredQuestionLocalSource
import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.repository.test.ITestRepository
import com.testeducation.domain.service.test.ITestService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object TestCoreModule {

    @Provides
    @Reusable
    fun provideTestRepository(
        remoteSource: ITestRemoteSource
    ): ITestRepository = TestRepository(remoteSource)

    @Provides
    @Reusable
    fun provideTestService(
        testRemoteClient: ITestRemoteClient,
        answeredQuestionLocalSource: IAnsweredQuestionLocalSource
    ): ITestService = TestService(
        testRemoteClient,
        answeredQuestionLocalSource
    )
}

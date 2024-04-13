package com.testeducation.education.di.modules.remote.test

import com.testeducation.core.client.remote.test.ITestRemoteClient
import com.testeducation.core.source.remote.question.ITestPassResultRemoteSource
import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.remote.client.remote.test.TestRemoteClient
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.source.question.TestPassResultRemoteSource
import com.testeducation.remote.source.test.TestRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object TestRemoteModule {

    @Provides
    @Reusable
    fun provideTestRemoteSource(
        testRetrofitClient: TestRetrofitClient
    ): ITestRemoteSource = TestRemoteSource(testRetrofitClient)

    @Provides
    @Reusable
    fun provideTestRemoteClient(
        testRetrofitClient: TestRetrofitClient
    ): ITestRemoteClient = TestRemoteClient(testRetrofitClient)

    @Provides
    @Reusable
    fun provideTestPassResultRemoteSource(
        remoteClient: TestRetrofitClient
    ): ITestPassResultRemoteSource = TestPassResultRemoteSource(remoteClient)

}

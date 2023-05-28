package com.testeducation.education.di.modules.remote.source.test

import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.remote.client.retrofit.test.TestRetrofitClient
import com.testeducation.remote.source.test.TestRemoteSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object TestRemoteSourceModule {

    @Provides
    @Reusable
    fun provideTestRemoteSource(
        testRetrofitClient: TestRetrofitClient
    ): ITestRemoteSource = TestRemoteSource(testRetrofitClient)

}

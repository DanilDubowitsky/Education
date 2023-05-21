package com.example.education.di.modules.remote.source.test

import com.example.core.source.remote.ITestRemoteSource
import com.example.remote.client.retrofit.test.TestRetrofitClient
import com.example.remote.source.test.TestRemoteSource
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

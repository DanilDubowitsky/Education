package com.example.education.di.modules.domain.repository.test

import com.example.core.repository.test.TestRepository
import com.example.core.source.remote.ITestRemoteSource
import com.example.domain.repository.test.ITestRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object TestRepositoryModule {

    @Provides
    @Reusable
    fun provideTestRepository(
        remoteSource: ITestRemoteSource
    ): ITestRepository = TestRepository(remoteSource)

}

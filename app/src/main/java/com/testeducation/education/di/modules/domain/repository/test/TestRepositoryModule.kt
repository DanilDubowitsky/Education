package com.testeducation.education.di.modules.domain.repository.test

import com.testeducation.core.repository.test.TestRepository
import com.testeducation.core.source.remote.test.ITestRemoteSource
import com.testeducation.domain.repository.test.ITestRepository
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

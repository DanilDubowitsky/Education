package com.testeducation.education.di.modules.domain.usecase.test

import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.repository.test.ITestRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object TestUseCaseModule {

    @Provides
    @Reusable
    fun provideGetTests(
        testRepository: ITestRepository
    ): GetTests = GetTests(testRepository)
}

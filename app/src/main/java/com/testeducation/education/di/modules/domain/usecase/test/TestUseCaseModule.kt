package com.testeducation.education.di.modules.domain.usecase.test

import com.testeducation.domain.cases.test.CreateTest
import com.testeducation.domain.cases.test.GetLikedTests
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.repository.test.ITestRepository
import com.testeducation.domain.service.test.ITestService
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

    @Provides
    @Reusable
    fun provideLikeTest(
        testService: ITestService
    ): ToggleTestLike = ToggleTestLike(testService)

    @Provides
    @Reusable
    fun provideGetLikedTests(
        testRepository: ITestRepository
    ): GetLikedTests = GetLikedTests(testRepository)

    @Provides
    @Reusable
    fun provideCreateTest(
        testService: ITestService
    ): CreateTest = CreateTest(testService)

}

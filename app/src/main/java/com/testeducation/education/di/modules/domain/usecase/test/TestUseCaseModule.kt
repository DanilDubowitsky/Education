package com.testeducation.education.di.modules.domain.usecase.test

import com.testeducation.domain.cases.test.CreateTest
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.GetTestSettings
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.test.UpdateTestSettings
import com.testeducation.domain.cases.test.UpdateTestStyle
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
    fun provideCreateTest(
        testService: ITestService
    ): CreateTest = CreateTest(testService)

    @Provides
    @Reusable
    fun provideGetTestDetails(
        testRepository: ITestRepository
    ): GetTest = GetTest(testRepository)

    @Provides
    @Reusable
    fun provideGetTestSettings(
        testRepository: ITestRepository
    ): GetTestSettings = GetTestSettings(testRepository)

    @Provides
    @Reusable
    fun provideUpdateTestSettings(
        testRepository: ITestRepository
    ): UpdateTestSettings = UpdateTestSettings(testRepository)

    @Provides
    @Reusable
    fun provideUpdateTestStyle(
        testRepository: ITestRepository
    ): UpdateTestStyle = UpdateTestStyle(testRepository)

}

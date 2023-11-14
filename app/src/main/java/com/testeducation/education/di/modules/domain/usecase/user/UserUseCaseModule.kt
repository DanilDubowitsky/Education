package com.testeducation.education.di.modules.domain.usecase.user

import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.cases.user.GetUserStatistics
import com.testeducation.domain.repository.user.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserUseCaseModule {

    @Provides
    @Reusable
    fun provideGetCurrentUser(
        userRepository: IUserRepository
    ): GetCurrentUser = GetCurrentUser(userRepository)

    @Provides
    @Reusable
    fun provideUserStatistics(
        userRepository: IUserRepository
    ) : GetUserStatistics = GetUserStatistics(userRepository)

}

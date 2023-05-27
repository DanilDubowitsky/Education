package com.example.education.di.modules.domain.usecase.user

import com.example.domain.cases.user.GetCurrentUser
import com.example.domain.repository.user.IUserRepository
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

}

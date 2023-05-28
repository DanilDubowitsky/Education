package com.example.education.di.modules.domain.repository.user

import com.example.core.repository.user.UserRepository
import com.example.core.source.remote.user.IUserRemoteSource
import com.example.domain.repository.user.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserRepositoryModule {

    @Provides
    @Reusable
    fun provideUserRepository(
        userRemoteSource: IUserRemoteSource
    ): IUserRepository = UserRepository(userRemoteSource)

}

package com.testeducation.education.di.modules.domain.repository.user

import com.testeducation.core.repository.user.UserRepository
import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.domain.repository.user.IUserRepository
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

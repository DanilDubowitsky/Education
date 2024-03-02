package com.testeducation.education.di.modules.core.repository.user

import com.testeducation.core.repository.user.UserConfirmCodeRepository
import com.testeducation.core.repository.user.UserRepository
import com.testeducation.core.source.remote.user.IUserConfirmCodeRemoteSource
import com.testeducation.core.source.remote.user.IUserRemoteSource
import com.testeducation.domain.repository.user.IUserConfirmCodeRepository
import com.testeducation.domain.repository.user.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object UserCoreModule {

    @Provides
    @Reusable
    fun provideUserRepository(
        userRemoteSource: IUserRemoteSource
    ): IUserRepository = UserRepository(userRemoteSource)

    @Provides
    @Reusable
    fun provideUserConfirmCodeRepository(
        userConfirmCodeRS: IUserConfirmCodeRemoteSource
    ): IUserConfirmCodeRepository = UserConfirmCodeRepository(userConfirmCodeRS)

}

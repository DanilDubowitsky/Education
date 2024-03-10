package com.testeducation.education.di.modules.domain.usecase.user

import com.testeducation.domain.cases.user.DeleteUser
import com.testeducation.domain.cases.user.DeleteUserConfirm
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.cases.user.GetUserStatistics
import com.testeducation.domain.cases.user.IsVisibleAvatar
import com.testeducation.domain.cases.user.SendSupport
import com.testeducation.domain.cases.user.SetAvatar
import com.testeducation.domain.cases.user.SetVisibleAvatar
import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.domain.repository.user.IUserConfirmCodeRepository
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
    ): GetUserStatistics = GetUserStatistics(userRepository)

    @Provides
    @Reusable
    fun provideSetAvatar(
        userRepository: IUserRepository
    ): SetAvatar = SetAvatar(userRepository)

    @Provides
    @Reusable
    fun provideSupportSender(
        userRepository: IUserRepository
    ): SendSupport = SendSupport(userRepository)

    @Provides
    @Reusable
    fun provideDeleteUser(
        userRepository: IUserConfirmCodeRepository
    ): DeleteUser = DeleteUser(userRepository)

    @Provides
    @Reusable
    fun provideDeleteUserConfirm(
        userRepository: IUserConfirmCodeRepository
    ): DeleteUserConfirm = DeleteUserConfirm(userRepository)

    @Provides
    @Reusable
    fun provideIsVisibleAvatar(
        userConfig: IUserConfig
    ): IsVisibleAvatar = IsVisibleAvatar(userConfig)

    @Provides
    @Reusable
    fun provideSetVisibleAvatar(
        userConfig: IUserConfig
    ): SetVisibleAvatar = SetVisibleAvatar(userConfig)

}

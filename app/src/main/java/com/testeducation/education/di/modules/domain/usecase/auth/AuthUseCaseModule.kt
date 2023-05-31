package com.testeducation.education.di.modules.domain.usecase.auth

import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.domain.cases.auth.SignIn
import com.testeducation.domain.cases.auth.SignUp
import com.testeducation.domain.config.IUserConfig
import com.testeducation.domain.service.auth.IAuthService
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object AuthUseCaseModule {

    @Provides
    @Reusable
    fun provideSignUp(
        service: IAuthService
    ): SignUp = SignUp(service)

    @Provides
    @Reusable
    fun provideConfirmEmail(
        service: IAuthService
    ): ConfirmEmail = ConfirmEmail(service)

    @Provides
    @Reusable
    fun provideSignIn(
        service: IAuthService,
        config: IUserConfig
    ): SignIn = SignIn(service, config)

}

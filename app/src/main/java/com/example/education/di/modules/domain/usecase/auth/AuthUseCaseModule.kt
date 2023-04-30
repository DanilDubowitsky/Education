package com.example.education.di.modules.domain.usecase.auth

import com.example.domain.cases.auth.SignUp
import com.example.domain.service.auth.IAuthService
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

}

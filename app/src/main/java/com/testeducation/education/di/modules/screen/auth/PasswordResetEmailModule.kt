package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SendResetPasswordCode
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.reset.email.PasswordResetEmailState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.auth.reset.email.PasswordResetEmailModelState
import com.testeducation.screen.auth.reset.email.PasswordResetEmailReducer
import com.testeducation.screen.auth.reset.email.PasswordResetEmailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface PasswordResetEmailModule {
    @Binds
    @IntoMap
    @ViewModelKey(PasswordResetEmailViewModel::class)
    fun bindViewModel(viewModel: PasswordResetEmailViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<PasswordResetEmailModelState, PasswordResetEmailState> =
            PasswordResetEmailReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            sendResetPasswordCode: SendResetPasswordCode,
            reducer: IReducer<PasswordResetEmailModelState, PasswordResetEmailState>,
            exceptionHandler: IExceptionHandler
        ): PasswordResetEmailViewModel = PasswordResetEmailViewModel(
            router,
            sendResetPasswordCode,
            reducer,
            exceptionHandler
        )
    }
}

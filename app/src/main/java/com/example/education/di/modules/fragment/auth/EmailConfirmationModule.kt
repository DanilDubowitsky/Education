package com.example.education.di.modules.fragment.auth

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.ConfirmEmail
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IErrorHandler
import com.example.helper.resource.IResourceHelper
import com.example.navigation.core.NavigationRouter
import com.example.screen.auth.confirmation.EmailConfirmationModelState
import com.example.screen.auth.confirmation.EmailConfirmationReducer
import com.example.screen.auth.confirmation.EmailConfirmationState
import com.example.screen.auth.confirmation.EmailConfirmationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface EmailConfirmationModule {
    @Binds
    @IntoMap
    @ViewModelKey(EmailConfirmationViewModel::class)
    fun bindViewModel(viewModel: EmailConfirmationViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<EmailConfirmationModelState, EmailConfirmationState> =
            EmailConfirmationReducer()

        @Provides
        fun provideViewModel(
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            confirmEmail: ConfirmEmail,
            reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>,
            errorHandler: IErrorHandler
        ): EmailConfirmationViewModel = EmailConfirmationViewModel(
            resourceHelper,
            router,
            confirmEmail,
            reducer,
            errorHandler
        )
    }
}

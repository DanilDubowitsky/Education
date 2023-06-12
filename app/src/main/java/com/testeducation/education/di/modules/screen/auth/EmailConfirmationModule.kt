package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.auth.confirmation.EmailConfirmationModelState
import com.testeducation.screen.auth.confirmation.EmailConfirmationReducer
import com.testeducation.screen.auth.confirmation.EmailConfirmationViewModel
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
            errorHandler: IExceptionHandler
        ): EmailConfirmationViewModel = EmailConfirmationViewModel(
            resourceHelper,
            router,
            confirmEmail,
            reducer,
            errorHandler
        )
    }
}

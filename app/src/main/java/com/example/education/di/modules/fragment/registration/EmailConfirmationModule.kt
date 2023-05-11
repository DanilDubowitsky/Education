package com.example.education.di.modules.fragment.registration

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.ConfirmEmail
import com.example.education.di.viewmodel.ViewModelKey
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
            router: NavigationRouter,
            confirmEmail: ConfirmEmail,
            reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>
        ): EmailConfirmationViewModel = EmailConfirmationViewModel(
            router,
            confirmEmail,
            reducer
        )
    }
}

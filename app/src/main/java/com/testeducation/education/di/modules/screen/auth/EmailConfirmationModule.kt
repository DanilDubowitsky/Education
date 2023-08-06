package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.domain.cases.auth.SendCodeAgain
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.auth.confirmation.EmailConfirmationModelState
import com.testeducation.screen.auth.confirmation.EmailConfirmationReducer
import com.testeducation.screen.auth.confirmation.EmailConfirmationViewModel
import com.testeducation.ui.screen.auth.confirmation.EmailConfirmationFragment
import com.testeducation.ui.utils.getScreen
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
            fragment: EmailConfirmationFragment,
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            confirmEmail: ConfirmEmail,
            sendCodeAgain: SendCodeAgain,
            reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>,
            errorHandler: IExceptionHandler
        ): EmailConfirmationViewModel {
            val screen = fragment.getScreen<NavigationScreen.Auth.EmailConfirmation>()
            return EmailConfirmationViewModel(
                screen.email,
                resourceHelper,
                router,
                confirmEmail,
                sendCodeAgain,
                reducer,
                errorHandler
            )
        }
    }
}

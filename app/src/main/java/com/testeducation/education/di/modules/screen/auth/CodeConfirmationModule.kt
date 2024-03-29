package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.domain.cases.auth.GetResetPasswordToken
import com.testeducation.domain.cases.auth.SendCodeAgain
import com.testeducation.domain.cases.auth.SignIn
import com.testeducation.domain.config.user.IRegistrationConfig
import com.testeducation.domain.config.user.IUserConfig
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.auth.confirmation.CodeConfirmationModelState
import com.testeducation.screen.auth.confirmation.CodeConfirmationReducer
import com.testeducation.screen.auth.confirmation.CodeConfirmationViewModel
import com.testeducation.ui.screen.auth.confirmation.CodeConfirmationFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface CodeConfirmationModule {
    @Binds
    @IntoMap
    @ViewModelKey(CodeConfirmationViewModel::class)
    fun bindViewModel(viewModel: CodeConfirmationViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<CodeConfirmationModelState, CodeConfirmationState> =
            CodeConfirmationReducer()

        @Provides
        fun provideViewModel(
            fragment: CodeConfirmationFragment,
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            confirmEmail: ConfirmEmail,
            getResetPasswordToken: GetResetPasswordToken,
            sendCodeAgain: SendCodeAgain,
            reducer: IReducer<CodeConfirmationModelState, CodeConfirmationState>,
            errorHandler: IExceptionHandler,
            registrationConfig: IRegistrationConfig,
            userConfig: IUserConfig,
            signIn: SignIn
        ): CodeConfirmationViewModel {
            val screen = fragment.getScreen<NavigationScreen.Auth.CodeConfirmation>()
            return CodeConfirmationViewModel(
                confirmationType = screen.confirmationType,
                email = screen.email,
                resourceHelper = resourceHelper,
                router = router,
                confirmEmail = confirmEmail,
                sendCodeAgain = sendCodeAgain,
                getResetPasswordToken = getResetPasswordToken,
                reducer = reducer,
                errorHandler = errorHandler,
                token = screen.token,
                signIn = signIn,
                registrationConfig = registrationConfig,
                userConfig = userConfig
            )
        }
    }
}

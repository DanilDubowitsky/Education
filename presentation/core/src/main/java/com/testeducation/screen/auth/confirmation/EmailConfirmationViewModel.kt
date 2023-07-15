package com.testeducation.screen.auth.confirmation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationSideEffect
import com.testeducation.logic.screen.auth.confirmation.EmailConfirmationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class EmailConfirmationViewModel(
    private val resourceHelper: IResourceHelper,
    private val router: NavigationRouter,
    private val confirmEmail: ConfirmEmail,
    reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<EmailConfirmationModelState,
        EmailConfirmationState, EmailConfirmationSideEffect>(reducer, errorHandler) {

    override val initialModelState: EmailConfirmationModelState = EmailConfirmationModelState()

    fun confirmEmail() = intent {
        val modelState = getModelState()
        if (modelState.code.isEmpty()) {
            postSideEffect(EmailConfirmationSideEffect.CodeValidationError)
            return@intent
        }
        updateModelState {
            copy(loadingState = EmailConfirmationModelState.LoadingState.LOADING)
        }
        confirmEmail(modelState.code)
        postSideEffect(EmailConfirmationSideEffect.RegistrationSuccess)
        router.newRootChain(NavigationScreen.Auth.Login)
    }

    fun onCodeTextChanged(text: String) = intent {
        updateModelState {
            copy(code = text)
        }
    }

    fun onBackPressed() = intent {
        val confirmationText =
            resourceHelper.extractStringResource(StringResource.Common.ConfirmExitString)
        val screen = NavigationScreen.Common.Confirmation(confirmationText)
        router.setResultListener(NavigationScreen.Common.Confirmation.OnConfirm) {
            router.exit()
        }
        router.navigateTo(screen)
    }

}

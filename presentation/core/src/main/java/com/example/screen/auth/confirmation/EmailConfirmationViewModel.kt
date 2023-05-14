package com.example.screen.auth.confirmation

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.ConfirmEmail
import com.example.helper.error.IErrorHandler
import com.example.helper.resource.IResourceHelper
import com.example.helper.resource.StringResource
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class EmailConfirmationViewModel(
    private val resourceHelper: IResourceHelper,
    private val router: NavigationRouter,
    private val confirmEmail: ConfirmEmail,
    reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>,
    errorHandler: IErrorHandler
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
        updateModelState {
            copy(loadingState = EmailConfirmationModelState.LoadingState.IDLE)
        }
        router.replace(NavigationScreen.Auth.Login)
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

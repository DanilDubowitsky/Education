package com.example.screen.auth.confirmation

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.ConfirmEmail
import com.example.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class EmailConfirmationViewModel(
    private val router: NavigationRouter,
    private val confirmEmail: ConfirmEmail,
    reducer: IReducer<EmailConfirmationModelState, EmailConfirmationState>
) : BaseViewModel<EmailConfirmationModelState,
        EmailConfirmationState, EmailConfirmationSideEffect>(reducer) {

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
    }

    fun onCodeTextChanged(text: String) = intent {
        updateModelState {
            copy(code = text)
        }
    }

    override fun handleThrowable(throwable: Throwable) {
        super.handleThrowable(throwable)

    }

}

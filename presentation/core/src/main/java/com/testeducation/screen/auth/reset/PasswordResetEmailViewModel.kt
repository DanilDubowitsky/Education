package com.testeducation.screen.auth.reset

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SendResetPasswordCode
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.model.auth.ConfirmationType
import com.testeducation.logic.screen.auth.reset.PasswordResetEmailSideEffect
import com.testeducation.logic.screen.auth.reset.PasswordResetEmailState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class PasswordResetEmailViewModel(
    private val router: NavigationRouter,
    private val sendResetPasswordCode: SendResetPasswordCode,
    reducer: IReducer<PasswordResetEmailModelState, PasswordResetEmailState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<PasswordResetEmailModelState,
        PasswordResetEmailState, PasswordResetEmailSideEffect>(reducer, exceptionHandler) {

    override val initialModelState: PasswordResetEmailModelState = PasswordResetEmailModelState()

    fun onInputEmailChanged(email: String) = intent {
        updateModelState {
            copy(inputEmail = email)
        }
    }

    fun send() = intent {
        val email = getModelState().inputEmail
        updateModelState {
            copy(loadingState = PasswordResetEmailModelState.LoadingState.LOADING)
        }
        sendResetPasswordCode(email)
        val screen = NavigationScreen.Auth.CodeConfirmation(
            email,
            ConfirmationType.PASSWORD_RESET
        )
        router.navigateTo(screen)
        updateModelState {
            copy(loadingState = PasswordResetEmailModelState.LoadingState.IDLE)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        super.handleThrowable(throwable)
        updateModelState {
            copy(loadingState = PasswordResetEmailModelState.LoadingState.IDLE)
        }
    }

}
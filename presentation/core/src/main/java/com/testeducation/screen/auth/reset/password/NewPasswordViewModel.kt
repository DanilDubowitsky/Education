package com.testeducation.screen.auth.reset.password

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ResetPassword
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.reset.password.NewPasswordSideEffect
import com.testeducation.logic.screen.auth.reset.password.NewPasswordState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.MIN_PASSWORD_LENGTH
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class NewPasswordViewModel(
    private val token: String,
    private val email: String,
    private val resetPassword: ResetPassword,
    private val router: NavigationRouter,
    reducer: IReducer<NewPasswordModelState, NewPasswordState>,
    exceptionHandler: IExceptionHandler
) : BaseViewModel<NewPasswordModelState, NewPasswordState, NewPasswordSideEffect>(
    reducer, exceptionHandler
) {

    override val initialModelState: NewPasswordModelState = NewPasswordModelState()

    fun resetPassword() = intent {
        val isValid = validatePassword()
        if (!isValid) return@intent
        val modelState = getModelState()
        updateModelState {
            copy(loadingState = NewPasswordModelState.LoadingState.LOADING)
        }
        resetPassword(email, modelState.password, modelState.repeatedPassword, token)
        postSideEffect(NewPasswordSideEffect.PasswordResetSuccess)
        router.newRootChain(NavigationScreen.Auth.Login)
    }

    fun onPasswordChanged(password: String) = intent {
        updateModelState {
            copy(password = password)
        }
    }

    fun onRepeatedPasswordChanged(password: String) = intent {
        updateModelState {
            copy(repeatedPassword = password)
        }
    }

    private suspend fun SimpleSyntax<NewPasswordState,
            NewPasswordSideEffect>.validatePassword(): Boolean {
        var isValid = true
        val modelState = getModelState()

        if (modelState.password.isEmpty() || modelState.password.length < MIN_PASSWORD_LENGTH) {
            isValid = false
            postSideEffect(NewPasswordSideEffect.PasswordInvalid)
        }
        if (modelState.repeatedPassword.isEmpty() || modelState.password.length < MIN_PASSWORD_LENGTH) {
            isValid = false
            postSideEffect(NewPasswordSideEffect.RepeatedPasswordInvalid)
        }

        if (modelState.password != modelState.repeatedPassword) {
            isValid = false
            postSideEffect(NewPasswordSideEffect.PasswordsNotMatch)
        }

        return isValid
    }

}
package com.example.screen.auth.login

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignIn
import com.example.helper.error.IErrorHandler
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class LoginViewModel(
    private val router: NavigationRouter,
    private val signIn: SignIn,
    reducer: IReducer<LoginModelState, LoginState>,
    errorHandler: IErrorHandler
) : BaseViewModel<LoginModelState, LoginState, LoginSideEffect>(reducer, errorHandler) {

    override val initialModelState: LoginModelState = LoginModelState()

    fun login() = intent {
        val modelState = getModelState()
        if (modelState.email.isNullOrEmpty() || modelState.password.isNullOrEmpty()) {
            return@intent
        }
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.LOADING)
        }
        signIn(modelState.email, modelState.password)
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.IDLE)
        }
    }

    fun onPasswordChanged(password: String) = intent {
        updateModelState {
            copy(password = password)
        }
    }

    fun onEmailChanged(email: String) = intent {
        updateModelState {
            copy(email = email)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        super.handleThrowable(throwable)
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.IDLE)
        }
    }

    fun registration() {
        router.navigateTo(NavigationScreen.Auth.Registration)
    }

}

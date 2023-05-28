package com.example.screen.auth.login

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignIn
import com.example.helper.error.IExceptionHandler
import com.example.helper.resource.IResourceHelper
import com.example.helper.resource.StringResource
import com.example.logic.screen.auth.login.LoginSideEffect
import com.example.logic.screen.auth.login.LoginState
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import com.example.utils.StringUtils.isEmptyBlank
import com.example.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class LoginViewModel(
    private val router: NavigationRouter,
    private val signIn: SignIn,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<LoginModelState, LoginState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<LoginModelState, LoginState, LoginSideEffect>(reducer, errorHandler) {

    override val initialModelState: LoginModelState = LoginModelState()

    fun login() = intent {
        val modelState = getModelState()
        val inputsNotValid = getValidInput(modelState)
        if (inputsNotValid) {
            return@intent
        }
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.LOADING)
        }
        signIn(modelState.email, modelState.password)
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.IDLE)
        }
        router.replace(NavigationScreen.Main.Home)
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

    private fun getValidInput(modelState: LoginModelState): Boolean {
        if (modelState.email.isEmptyBlank()) {
            intent {
                postSideEffect(
                    LoginSideEffect.EmailInputError(
                        StringResource.Error.EmailIsEmptyString.getString(resourceHelper)
                    )
                )
            }
        }

        if (modelState.password.isEmptyBlank()) {
            intent {
                postSideEffect(
                    LoginSideEffect.PasswordInputError(
                        StringResource.Error.PasswordIsEmptyString.getString(resourceHelper)
                    )
                )
            }
        }

        return modelState.email.isEmptyBlank() || modelState.password.isEmptyBlank()
    }

    fun registration() {
        router.navigateTo(NavigationScreen.Auth.Registration)
    }

}

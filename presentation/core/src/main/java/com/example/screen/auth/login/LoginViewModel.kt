package com.example.screen.auth.login

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignIn
import com.example.logic.model.common.InputState
import com.example.helper.error.IExceptionHandler
import com.example.helper.resource.IResourceHelper
import com.example.helper.resource.StringResource
import com.example.helper.resource.StringResource.Error.EmailIsEmptyString.getString
import com.example.logic.screen.auth.login.LoginSideEffect
import com.example.logic.screen.auth.login.LoginState
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import kotlinx.coroutines.flow.MutableStateFlow
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class LoginViewModel(
    private val router: NavigationRouter,
    private val signIn: SignIn,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<LoginModelState, LoginState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<LoginModelState, LoginState, LoginSideEffect>(reducer, errorHandler) {

    companion object {
        const val EMAIL_FIELD = "email"
        const val PASSWORD_FIELD = "password"
    }

    override val initialModelState: LoginModelState = LoginModelState()

    fun login() = intent {
        val modelState = getModelState()
        val resultValid = getValidInput(modelState)
        if (resultValid.isNotEmpty()) {
            postSideEffect(
                LoginSideEffect.ErrorTextInput(
                    resultValid
                )
            )
            return@intent
        }
        updateModelState {
            copy(loadingState = LoginModelState.LoadingState.LOADING)
        }
        signIn(modelState.email.orEmpty(), modelState.password.orEmpty())
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

    private fun getValidInput(modelState: LoginModelState) : Map<String, String> {
        val emailMapError = if (modelState.email.isNullOrEmpty()) {
            mapOf(EMAIL_FIELD to StringResource.Error.EmailIsEmptyString.getString(resourceHelper))
        } else mapOf()

        val passwordMapError = if (modelState.password.isNullOrEmpty()) {
            val errorMsg = StringResource.Error.PasswordIsEmptyString.getString(resourceHelper)
            mapOf(PASSWORD_FIELD to errorMsg)
        } else mapOf()

        val resultError = emailMapError.toMutableMap().apply {
            putAll(passwordMapError)
        }

        return resultError
    }

    fun registration() {
        router.navigateTo(NavigationScreen.Auth.Registration)
    }

}

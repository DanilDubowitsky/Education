package com.testeducation.screen.auth.registration

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SignUp
import com.testeducation.domain.exception.ServerException
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.registration.RegistrationSideEffect
import com.testeducation.logic.screen.auth.registration.RegistrationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class RegistrationViewModel(
    private val router: NavigationRouter,
    private val signUp: SignUp,
    reducer: IReducer<RegistrationModelState, RegistrationState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<RegistrationModelState, RegistrationState, RegistrationSideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: RegistrationModelState = RegistrationModelState()

    fun register() = intent {
        val modelState = getModelState()
        val isValid = validate(
            modelState.userName,
            modelState.password,
            modelState.email,
            modelState.confirmPassword
        )
        if (!isValid) return@intent
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.LOADING)
        }
        signUp(
            modelState.userName!!,
            modelState.email!!,
            modelState.password!!,
            modelState.confirmPassword!!
        )

        val screen = NavigationScreen.Auth.EmailConfirmation(modelState.email)
        router.navigateTo(screen)

        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
    }

    fun onPasswordChanged(password: String?) = intent {
        updateModelState {
            copy(password = password)
        }
    }

    fun onEmailChanged(email: String?) = intent {
        updateModelState {
            copy(email = password)
        }
    }

    fun onUserNameChanged(userName: String?) = intent {
        updateModelState {
            copy(userName = password)
        }
    }

    fun onRepeatedPasswordChanged(password: String?) = intent {
        updateModelState {
            copy(confirmPassword = password)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        val email = getModelState().email ?: return@intent
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
        if (throwable is ServerException) {
            if (throwable.displayMessage == REGISTRATION_CODE_ALREADY_SEND_ERROR) {
                val screen = NavigationScreen.Auth.EmailConfirmation(email)
                router.navigateTo(screen)
                return@intent
            } else super.handleThrowable(throwable)
        }
    }

    //todo Переписать валидацию
    private fun validate(
        nickName: String?,
        password: String?,
        userName: String?,
        repeatedPassword: String?
    ): Boolean =
        !nickName.isNullOrEmpty()
                && !password.isNullOrEmpty()
                && !userName.isNullOrEmpty()
                && password == repeatedPassword

    private companion object {
        const val REGISTRATION_CODE_ALREADY_SEND_ERROR = "Registration code has already been sent"
    }

}

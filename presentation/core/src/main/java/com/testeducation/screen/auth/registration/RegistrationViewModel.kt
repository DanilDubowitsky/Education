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

    fun register(
        nickName: String,
        password: String,
        email: String,
        repeatedPassword: String
    ) = intent {
        val isValid = validate(nickName, password, email, repeatedPassword)
        if (!isValid) return@intent
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.LOADING)
        }
        signUp(nickName, email, password, repeatedPassword)

        val screen = NavigationScreen.Auth.EmailConfirmation
        router.navigateTo(screen)

        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
        if (throwable is ServerException) {
            if (throwable.displayMessage == REGISTRATION_CODE_ALREADY_SEND_ERROR) {
                router.navigateTo(NavigationScreen.Auth.EmailConfirmation)
                return@intent
            } else super.handleThrowable(throwable)
        }
    }

    //todo Переписать валидацию
    private fun validate(
        nickName: String,
        password: String,
        userName: String,
        repeatedPassword: String
    ): Boolean =
        nickName.isNotEmpty()
                && password.isNotEmpty()
                && userName.isNotEmpty()
                && password == repeatedPassword

    private companion object {
        const val REGISTRATION_CODE_ALREADY_SEND_ERROR = "Registration code has already been sent"
    }

}

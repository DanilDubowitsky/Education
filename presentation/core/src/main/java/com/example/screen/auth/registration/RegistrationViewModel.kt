package com.example.screen.auth.registration

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignUp
import com.example.domain.exception.ServerException
import com.example.navigation.core.NavigationRouter
import com.example.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class RegistrationViewModel(
    private val router: NavigationRouter,
    private val signUp: SignUp,
    reducer: IReducer<RegistrationModelState, RegistrationState>
) : BaseViewModel<RegistrationModelState, RegistrationState, RegistrationSideEffect>(
    reducer
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
            }
            postSideEffect(
                RegistrationSideEffect.ShowMessage(
                    throwable.displayMessage
                )
            )
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

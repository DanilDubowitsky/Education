package com.example.screen.registration

import com.example.core.BaseViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignUp
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class RegistrationViewModel(
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
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
    }

    override fun handleThrowable(throwable: Throwable) = intent {
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.IDLE)
        }
        postSideEffect(
            RegistrationSideEffect.ShowMessage(
                throwable.message.orEmpty()
            )
        )
    }

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

}

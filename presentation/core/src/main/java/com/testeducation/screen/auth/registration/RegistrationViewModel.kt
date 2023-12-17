package com.testeducation.screen.auth.registration

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SignUp
import com.testeducation.domain.exception.ServerException
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.auth.ConfirmationType
import com.testeducation.logic.screen.auth.registration.RegistrationSideEffect
import com.testeducation.logic.screen.auth.registration.RegistrationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent

class RegistrationViewModel(
    private val router: NavigationRouter,
    private val signUp: SignUp,
    reducer: IReducer<RegistrationModelState, RegistrationState>,
    errorHandler: IExceptionHandler,
    private val resourceHelper: IResourceHelper
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
        if (!isValid) {
            router.navigateTo(
                NavigationScreen.Common.Information(
                    StringResource.Validate.RegistrationEmptyData.getString(
                        resourceHelper = resourceHelper
                    ),
                    titleText = StringResource.Validate.ValidateTitleAttention.getString(
                        resourceHelper
                    )
                )
            )
            return@intent
        }
        updateModelState {
            copy(loadingState = RegistrationModelState.LoadingState.LOADING)
        }
        signUp(
            modelState.userName!!,
            modelState.email!!,
            modelState.password!!,
            modelState.confirmPassword!!
        ).also { token ->
            val screen = NavigationScreen.Auth.CodeConfirmation(
                modelState.email,
                token,
                ConfirmationType.EMAIL_CONFIRMATION
            )
            router.navigateTo(screen)

            updateModelState {
                copy(loadingState = RegistrationModelState.LoadingState.IDLE)
            }
        }
    }

    fun onPasswordChanged(password: String?) = intent {
        updateModelState {
            copy(password = password)
        }
    }

    fun onEmailChanged(email: String?) = intent {
        updateModelState {
            copy(email = email)
        }
    }

    fun onUserNameChanged(userName: String?) = intent {
        updateModelState {
            copy(userName = userName)
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
            super.handleThrowable(throwable)
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

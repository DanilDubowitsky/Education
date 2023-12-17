package com.testeducation.screen.auth.confirmation

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ConfirmEmail
import com.testeducation.domain.cases.auth.GetResetPasswordToken
import com.testeducation.domain.cases.auth.SendCodeAgain
import com.testeducation.domain.utils.MINUTE_IN_MILLIS
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.auth.ConfirmationType
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationSideEffect
import com.testeducation.logic.screen.auth.confirmation.CodeConfirmationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class CodeConfirmationViewModel(
    private val confirmationType: ConfirmationType,
    private val email: String,
    private val resourceHelper: IResourceHelper,
    private val router: NavigationRouter,
    private val confirmEmail: ConfirmEmail,
    private val sendCodeAgain: SendCodeAgain,
    private val getResetPasswordToken: GetResetPasswordToken,
    reducer: IReducer<CodeConfirmationModelState, CodeConfirmationState>,
    errorHandler: IExceptionHandler,
    private val token: String
) : BaseViewModel<CodeConfirmationModelState,
        CodeConfirmationState, CodeConfirmationSideEffect>(reducer, errorHandler) {

    override val initialModelState: CodeConfirmationModelState = CodeConfirmationModelState()

    init {
        initConfirmationData()
    }

    fun send() = intent {
        when (confirmationType) {
            ConfirmationType.PASSWORD_RESET -> confirmPasswordReset()
            ConfirmationType.EMAIL_CONFIRMATION -> confirmEmail()
        }
    }

    fun onCodeChanged(text: String) = intent {
        updateModelState {
            copy(code = text)
        }
    }

    fun onBackPressed() = intent {
        val confirmationText =
            resourceHelper.extractStringResource(StringResource.Common.ConfirmExitString)
        val screen = NavigationScreen.Common.Confirmation(confirmationText)
        router.setResultListener(NavigationScreen.Common.Confirmation.OnConfirm) {
            router.exit()
        }
        router.navigateTo(screen)
    }

    fun onCodeExpired() = intent {
        updateModelState {
            copy(isCodeExpired = true)
        }
    }

    fun sendCodeAgain() = intent {
        updateModelState {
            copy(loadingState = CodeConfirmationModelState.LoadingState.LOADING)
        }
        sendCodeAgain(email)
        initConfirmationData()
        updateModelState {
            copy(
                loadingState = CodeConfirmationModelState.LoadingState.IDLE,
                isCodeExpired = false,
                code = null
            )
        }
    }

    private suspend fun SimpleSyntax<CodeConfirmationState, CodeConfirmationSideEffect>.confirmEmail() {
        val modelState = getModelState()
        if (modelState.code.isNullOrEmpty()) {
            postSideEffect(CodeConfirmationSideEffect.CodeValidationError)
            return
        }
        updateModelState {
            copy(loadingState = CodeConfirmationModelState.LoadingState.LOADING)
        }
        confirmEmail(modelState.code, email, token)
        postSideEffect(CodeConfirmationSideEffect.RegistrationSuccess)
        router.newRootChain(NavigationScreen.Auth.Login)
    }

    private suspend fun SimpleSyntax<CodeConfirmationState, CodeConfirmationSideEffect>.confirmPasswordReset() {
        val modelState = getModelState()
        if (modelState.code.isNullOrEmpty()) {
            postSideEffect(CodeConfirmationSideEffect.CodeValidationError)
            return
        }
        updateModelState {
            copy(loadingState = CodeConfirmationModelState.LoadingState.LOADING)
        }
        val screen = NavigationScreen.Auth.NewPassword(email, modelState.code)
        router.navigateTo(screen, false)
        updateModelState {
            copy(loadingState = CodeConfirmationModelState.LoadingState.IDLE)
        }
    }

    private fun initConfirmationData() = intent {
        val expireTime = when (confirmationType) {
            ConfirmationType.PASSWORD_RESET -> PASSWORD_RESET_CODE_EXPIRE_TIME
            ConfirmationType.EMAIL_CONFIRMATION -> EMAIL_CONFIRMATION_CODE_EXPIRE_TIME
        }
        val sideEffect = CodeConfirmationSideEffect.StartTimer(
            expireTime
        )
        postSideEffect(sideEffect)
    }

    private companion object {
        const val EMAIL_CONFIRMATION_CODE_EXPIRE_TIME = MINUTE_IN_MILLIS
        const val PASSWORD_RESET_CODE_EXPIRE_TIME = 2 * MINUTE_IN_MILLIS
    }

}

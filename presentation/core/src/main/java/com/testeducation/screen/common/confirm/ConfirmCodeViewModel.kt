package com.testeducation.screen.common.confirm

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.DeleteUser
import com.testeducation.domain.cases.user.DeleteUserConfirm
import com.testeducation.domain.config.user.IConfirmCodeConfig
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState
import com.testeducation.logic.screen.common.confirm.ConfirmSideEffect
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import java.util.concurrent.TimeUnit

class ConfirmCodeViewModel(
    reducer: IReducer<ConfirmCodeModelState, ConfirmCodeState>,
    exceptionHandler: IExceptionHandler,
    title: String,
    description: String,
    private val deleteUser: DeleteUser,
    private val confirmDeleteUser: DeleteUserConfirm,
    private val confirmCodeConfig: IConfirmCodeConfig,
    private val router: NavigationRouter,
) : BaseViewModel<ConfirmCodeModelState, ConfirmCodeState, ConfirmSideEffect>(
    reducer,
    exceptionHandler
) {
    override val initialModelState: ConfirmCodeModelState =
        ConfirmCodeModelState(title, description)

    override fun handleThrowable(throwable: Throwable) {
        router.exit()
        super.handleThrowable(throwable)
    }

    init {
        intent {
            val time = confirmCodeConfig.getTime()
            val currentTime = System.currentTimeMillis()
            val betweenTime = currentTime - time
            when {
                time == confirmCodeConfig.defaultTime -> {
                    deleteUserIntent()
                }

                betweenTime < TimeUnit.MINUTES.toMillis(2) -> {
                    updateModelState {
                        copy(
                            isLoading = false
                        )
                    }
                    postSideEffect(
                        ConfirmSideEffect.StartTime(
                            TimeUnit.MINUTES.toMillis(2) - betweenTime
                        )
                    )
                }

                else -> {
                    deleteUserIntent()
                }
            }
        }
    }

    fun onCodeExpired() = intent {
        updateModelState {
            copy(sendCodeRetry = true)
        }
    }

    fun onCodeChanged(text: String) = intent {
        updateModelState {
            copy(code = text)
        }
    }

    fun sendCodeAgain() = intent {
        updateModelState {
            copy(
                sendCodeRetry = false,
                code = "",
                isLoading = true
            )
        }
        deleteUserIntent()
    }

    fun confirm() = intent {
        updateModelState {
            copy(
                isLoading = true
            )
        }
        val modelState = getModelState()
        confirmDeleteUser(token = modelState.token, code = modelState.code)
        confirmCodeConfig.setTime(-1)
        router.sendResult(NavigationScreen.Common.ConfirmCode.OnConfirm, Unit)
        router.exit()
    }

    private suspend fun SimpleSyntax<ConfirmCodeState, ConfirmSideEffect>.deleteUserIntent(
        time: Long = TimeUnit.MINUTES.toMillis(
            2
        )
    ) {
        deleteUser().also { result ->
            updateModelState {
                copy(
                    token = result,
                    isLoading = false,
                    sendCodeRetry = false
                )
            }
        }
        confirmCodeConfig.setTime(System.currentTimeMillis())
        postSideEffect(
            ConfirmSideEffect.StartTime(
                time
            )
        )
    }


}
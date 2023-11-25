package com.testeducation.screen.profile.support

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.SendSupport
import com.testeducation.domain.model.profile.CategorySupport
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.profile.support.SupportSenderState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class SupportSenderViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<SupportSenderModelState, SupportSenderState>,
    errorHandler: IExceptionHandler,
    private val sendSupport: SendSupport
) : BaseViewModel<SupportSenderModelState, SupportSenderState, ProfileSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: SupportSenderModelState = SupportSenderModelState()

    override fun handleThrowable(throwable: Throwable) {
        super.handleThrowable(throwable)
        intent {
            updateModelState {
                copy(
                    isLoading = false
                )
            }
        }
    }

    fun back() {
        router.exit()
    }

    fun send() = intent {
        updateModelState {
            copy(
                isLoading = true
            )
        }
        val modelState = getModelState()
        sendSupport(modelState.text, modelState.categorySupport)
        back()
    }

    fun changeCategory(isBug: Boolean) = intent {
        updateModelState {
            copy(
                categorySupport = if (isBug) CategorySupport.Bug else CategorySupport.Wish
            )
        }
    }

    fun onTextChanged(text: String) = intent {
        updateModelState {
            copy(
                text = text
            )
        }
    }
}
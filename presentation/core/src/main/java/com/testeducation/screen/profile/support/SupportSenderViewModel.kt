package com.testeducation.screen.profile.support

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.SendSupport
import com.testeducation.domain.model.profile.CategorySupport
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.screen.profile.support.SupportSenderSideEffect
import com.testeducation.logic.screen.profile.support.SupportSenderState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class SupportSenderViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<SupportSenderModelState, SupportSenderState>,
    errorHandler: IExceptionHandler,
    private val sendSupport: SendSupport,
    private val resources: IResourceHelper
) : BaseViewModel<SupportSenderModelState, SupportSenderState, SupportSenderSideEffect>(
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
        postSideEffect(SupportSenderSideEffect.ClearFocus)
        updateModelState {
            copy(
                isLoading = true
            )
        }
        val modelState = getModelState()
        sendSupport(modelState.text, modelState.categorySupport)
        updateModelState {
            copy(
                isLoading = false
            )
        }
        router.navigateTo(
            NavigationScreen.Common.Information(
                titleText = StringResource.BugReport.TitleInfoSuccess.getString(resources),
                description = StringResource.BugReport.DescriptionInfoSuccess.getString(resources),
                btnText = StringResource.Common.Close.getString(resources)
            )
        )
    }

    fun changeCategory(category: SupportSenderState.CategoryUi) = intent {
        when(category) {
            SupportSenderState.CategoryUi.Message -> CategorySupport.Message
            SupportSenderState.CategoryUi.Bug -> CategorySupport.Bug
            SupportSenderState.CategoryUi.Wish -> CategorySupport.Wish
        }.let {
            updateModelState {
                copy(
                    categorySupport = it
                )
            }
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
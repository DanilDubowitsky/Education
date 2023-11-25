package com.testeducation.screen.webview

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.webview.WebViewState
import com.testeducation.navigation.core.NavigationRouter

class WebViewViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<WebViewModelState, WebViewState>,
    errorHandler: IExceptionHandler,
    private val url: String
) : BaseViewModel<WebViewModelState, WebViewState, ProfileSideEffect>(reducer, errorHandler) {
    override val initialModelState: WebViewModelState = WebViewModelState(url)

    fun back() {
        router.exit()
    }
}
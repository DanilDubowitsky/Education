package com.testeducation.screen.webview

import com.testeducation.core.IReducer
import com.testeducation.logic.screen.webview.WebViewState

class WebViewReducer : IReducer<WebViewModelState, WebViewState> {
    override fun reduce(modelState: WebViewModelState): WebViewState {
        return WebViewState(
            modelState.url
        )
    }
}
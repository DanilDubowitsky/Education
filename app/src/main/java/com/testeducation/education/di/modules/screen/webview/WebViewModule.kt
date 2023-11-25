package com.testeducation.education.di.modules.screen.webview

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.webview.WebViewState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.webview.WebViewModelState
import com.testeducation.screen.webview.WebViewReducer
import com.testeducation.screen.webview.WebViewViewModel
import com.testeducation.ui.screen.webview.WebViewFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface WebViewModule {
    @Binds
    @IntoMap
    @ViewModelKey(WebViewViewModel::class)
    fun bindViewModel(viewModel: WebViewViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<WebViewModelState, WebViewState> =
            WebViewReducer()

        @Provides
        fun provideViewModel(
            fragment: WebViewFragment,
            router: NavigationRouter,
            reducer: IReducer<WebViewModelState, WebViewState>,
            errorHandler: IExceptionHandler,
        ): WebViewViewModel {
            val screen = fragment.getScreen<NavigationScreen.Common.WebView>()
            return WebViewViewModel(
                router, reducer, errorHandler, screen.url
            )
        }
    }
}
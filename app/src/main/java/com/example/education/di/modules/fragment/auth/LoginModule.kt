package com.example.education.di.modules.fragment.auth

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignIn
import com.example.education.di.viewmodel.ViewModelKey
import com.example.helper.error.IExceptionHandler
import com.example.helper.resource.IResourceHelper
import com.example.navigation.core.NavigationRouter
import com.example.screen.auth.login.LoginModelState
import com.example.screen.auth.login.LoginReducer
import com.example.screen.auth.login.LoginViewModel
import com.example.ui.helper.resource.ResourceHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LoginModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindViewModel(viewModel: LoginViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<LoginModelState, com.example.logic.screen.auth.login.LoginState> =
            LoginReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            signIn: SignIn,
            reducer: IReducer<LoginModelState, com.example.logic.screen.auth.login.LoginState>,
            resourceHelper: IResourceHelper,
            errorHandler: IExceptionHandler
        ): LoginViewModel = LoginViewModel(
            router,
            signIn,
            resourceHelper,
            reducer,
            errorHandler
        )
    }
}

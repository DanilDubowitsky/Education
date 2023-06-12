package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SignIn
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.auth.login.LoginState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.auth.login.LoginModelState
import com.testeducation.screen.auth.login.LoginReducer
import com.testeducation.screen.auth.login.LoginViewModel
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
        fun provideReducer(): IReducer<LoginModelState, LoginState> =
            LoginReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            signIn: SignIn,
            reducer: IReducer<LoginModelState, LoginState>,
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

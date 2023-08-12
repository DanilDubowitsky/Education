package com.testeducation.education.di.modules.screen.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.ResetPassword
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.reset.password.NewPasswordState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.auth.reset.password.NewPasswordModelState
import com.testeducation.screen.auth.reset.password.NewPasswordReducer
import com.testeducation.screen.auth.reset.password.NewPasswordViewModel
import com.testeducation.ui.screen.auth.reset.password.NewPasswordFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface NewPasswordModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewPasswordViewModel::class)
    fun bindViewModel(viewModel: NewPasswordViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<NewPasswordModelState, NewPasswordState> =
            NewPasswordReducer()

        @Provides
        fun provideViewModel(
            fragment: NewPasswordFragment,
            router: NavigationRouter,
            resetPassword: ResetPassword,
            reducer: IReducer<NewPasswordModelState, NewPasswordState>,
            exceptionHandler: IExceptionHandler
        ): NewPasswordViewModel {
            val screen = fragment.getScreen<NavigationScreen.Auth.NewPassword>()

            return NewPasswordViewModel(
                screen.token,
                screen.email,
                resetPassword,
                router,
                reducer,
                exceptionHandler
            )
        }
    }
}

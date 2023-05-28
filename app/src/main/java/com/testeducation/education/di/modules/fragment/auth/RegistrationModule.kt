package com.testeducation.education.di.modules.fragment.auth

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.SignUp
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.auth.registration.RegistrationState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.auth.registration.RegistrationModelState
import com.testeducation.screen.auth.registration.RegistrationReducer
import com.testeducation.screen.auth.registration.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface RegistrationModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindViewModel(viewModel: RegistrationViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<RegistrationModelState, RegistrationState> =
            RegistrationReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            signUp: SignUp,
            reducer: IReducer<RegistrationModelState, RegistrationState>,
            errorHandler: IExceptionHandler
        ): RegistrationViewModel = RegistrationViewModel(
            router,
            signUp,
            reducer,
            errorHandler
        )
    }
}

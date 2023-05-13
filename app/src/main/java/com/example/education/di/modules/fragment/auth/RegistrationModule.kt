package com.example.education.di.modules.fragment.auth

import androidx.lifecycle.ViewModel
import com.example.core.IReducer
import com.example.domain.cases.auth.SignUp
import com.example.education.di.viewmodel.ViewModelKey
import com.example.navigation.core.NavigationRouter
import com.example.screen.auth.registration.RegistrationModelState
import com.example.screen.auth.registration.RegistrationReducer
import com.example.screen.auth.registration.RegistrationState
import com.example.screen.auth.registration.RegistrationViewModel
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
            reducer: IReducer<RegistrationModelState, RegistrationState>
        ): RegistrationViewModel = RegistrationViewModel(
            router,
            signUp,
            reducer
        )
    }
}

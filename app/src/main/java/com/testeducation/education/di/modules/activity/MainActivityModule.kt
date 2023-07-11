package com.testeducation.education.di.modules.activity

import androidx.lifecycle.ViewModel
import com.testeducation.activity.main.MainActivityModelState
import com.testeducation.activity.main.MainActivityReducer
import com.testeducation.logic.activity.MainActivityState
import com.testeducation.activity.main.MainActivityViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.GetTokenExpiration
import com.testeducation.domain.cases.internal.IsAppVersionUpdateRequired
import com.testeducation.domain.interaction.user.UserConfigInteractor
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.navigation.core.NavigationRouter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun bindViewModel(viewModel: MainActivityViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<MainActivityModelState, MainActivityState> =
            MainActivityReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            userConfigInteractor: UserConfigInteractor,
            resourceHelper: IResourceHelper,
            isAppVersionUpdateRequired: IsAppVersionUpdateRequired,
            getTokenExpiration: GetTokenExpiration,
            reducer: IReducer<MainActivityModelState, MainActivityState>,
            errorHandler: IExceptionHandler
        ): MainActivityViewModel = MainActivityViewModel(
            router,
            userConfigInteractor,
            resourceHelper,
            isAppVersionUpdateRequired,
            getTokenExpiration,
            reducer,
            errorHandler
        )
    }
}

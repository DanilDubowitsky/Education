package com.testeducation.education.di.modules.screen.profile

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.about.AboutAppState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.profile.about.AboutAppModelState
import com.testeducation.screen.profile.about.AboutAppReducer
import com.testeducation.screen.profile.about.AboutAppViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface AboutAppModule {
    @Binds
    @IntoMap
    @ViewModelKey(AboutAppViewModel::class)
    fun bindViewModel(viewModel: AboutAppViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<AboutAppModelState, AboutAppState> =
            AboutAppReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<AboutAppModelState, AboutAppState>,
            errorHandler: IExceptionHandler,
        ): AboutAppViewModel {
            return AboutAppViewModel(
                router = router,
                reducer = reducer,
                errorHandler = errorHandler,
            )
        }
    }
}
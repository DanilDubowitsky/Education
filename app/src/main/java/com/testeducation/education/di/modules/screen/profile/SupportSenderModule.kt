package com.testeducation.education.di.modules.screen.profile

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.SendSupport
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.support.SupportSenderState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.profile.support.SupportSenderModelState
import com.testeducation.screen.profile.support.SupportSenderReducer
import com.testeducation.screen.profile.support.SupportSenderViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface SupportSenderModule {
    @Binds
    @IntoMap
    @ViewModelKey(SupportSenderViewModel::class)
    fun bindViewModel(viewModel: SupportSenderViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<SupportSenderModelState, SupportSenderState> =
            SupportSenderReducer()

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<SupportSenderModelState, SupportSenderState>,
            errorHandler: IExceptionHandler,
            sendSupport: SendSupport
        ): SupportSenderViewModel {
            return SupportSenderViewModel(
                router, reducer, errorHandler, sendSupport
            )
        }
    }
}
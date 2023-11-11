package com.testeducation.education.di.modules.screen.profile

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.ProfileState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.profile.ProfileModelState
import com.testeducation.screen.profile.ProfileReducer
import com.testeducation.screen.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindViewModel(viewModel: ProfileViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<ProfileModelState, ProfileState> =
            ProfileReducer()

        @Provides
        fun provideViewModel(
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            reducer: IReducer<ProfileModelState, ProfileState>,
            errorHandler: IExceptionHandler,
            getCurrentUser: GetCurrentUser
        ): ProfileViewModel {
            return ProfileViewModel(
                router, resourceHelper, reducer, errorHandler, getCurrentUser
            )
        }
    }
}
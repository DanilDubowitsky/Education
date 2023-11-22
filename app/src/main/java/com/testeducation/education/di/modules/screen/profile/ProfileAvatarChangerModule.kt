package com.testeducation.education.di.modules.screen.profile

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.avatar.AvatarHelper
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.profile.avatar.ProfileAvatarChangerModelState
import com.testeducation.screen.profile.avatar.ProfileAvatarChangerReducer
import com.testeducation.screen.profile.avatar.ProfileAvatarChangerViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ProfileAvatarChangerModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileAvatarChangerViewModel::class)
    fun bindViewModel(viewModel: ProfileAvatarChangerViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(avatarHelper: IAvatarHelper): IReducer<ProfileAvatarChangerModelState, ProfileAvatarChangerState> =
            ProfileAvatarChangerReducer(avatarHelper)

        @Provides
        fun profileAvatarHelper(resourceHelper: IResourceHelper): IAvatarHelper =
            AvatarHelper(resourceHelper)

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            reducer: IReducer<ProfileAvatarChangerModelState, ProfileAvatarChangerState>,
            errorHandler: IExceptionHandler,
        ): ProfileAvatarChangerViewModel {
            return ProfileAvatarChangerViewModel(
                router = router,
                reducer = reducer,
                errorHandler = errorHandler
            )
        }
    }
}
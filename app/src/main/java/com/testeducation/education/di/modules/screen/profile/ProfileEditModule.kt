package com.testeducation.education.di.modules.screen.profile

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.LogOut
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.avatar.AvatarHelper
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.edit.ProfileEditState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.profile.edit.ProfileEditModelState
import com.testeducation.screen.profile.edit.ProfileEditReducer
import com.testeducation.screen.profile.edit.ProfileEditViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ProfileEditModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileEditViewModel::class)
    fun bindViewModel(viewModel: ProfileEditViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(avatarHelper: IAvatarHelper): IReducer<ProfileEditModelState, ProfileEditState> =
            ProfileEditReducer(avatarHelper)

        @Provides
        fun profileAvatarHelper(resourceHelper: IResourceHelper): IAvatarHelper =
            AvatarHelper(resourceHelper)

        @Provides
        fun provideViewModel(
            resourceHelper: IResourceHelper,
            router: NavigationRouter,
            reducer: IReducer<ProfileEditModelState, ProfileEditState>,
            errorHandler: IExceptionHandler,
            getCurrentUser: GetCurrentUser,
            logOut: LogOut
        ): ProfileEditViewModel {
            return ProfileEditViewModel(
                router = router,
                resourceHelper = resourceHelper,
                reducer = reducer,
                errorHandler = errorHandler,
                getCurrentUser = getCurrentUser,
                logOut = logOut
            )
        }
    }
}
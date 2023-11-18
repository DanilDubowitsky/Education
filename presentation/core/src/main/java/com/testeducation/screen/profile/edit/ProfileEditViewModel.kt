package com.testeducation.screen.profile.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.profile.edit.ProfileEditState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileEditViewModel(
    private val router: NavigationRouter,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<ProfileEditModelState, ProfileEditState>,
    errorHandler: IExceptionHandler,
    private val getCurrentUser: GetCurrentUser,
) : BaseViewModel<ProfileEditModelState, ProfileEditState, ProfileSideEffect>(reducer, errorHandler) {
    override val initialModelState: ProfileEditModelState = ProfileEditModelState()

    init {
        initDataProfile()
    }

    fun back() {
        router.exit()
    }

    private fun initDataProfile() = intent {
        val currentUser = getCurrentUser.invoke()
        updateModelState {
            copy(
                user = currentUser,
            )
        }
    }
}
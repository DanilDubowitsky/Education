package com.testeducation.screen.profile

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.profile.ProfileState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileViewModel(
    private val router: NavigationRouter,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<ProfileModelState, ProfileState>,
    errorHandler: IExceptionHandler,
    private val getCurrentUser: GetCurrentUser,
) : BaseViewModel<ProfileModelState, ProfileState, ProfileSideEffect>(reducer, errorHandler) {
    override val initialModelState: ProfileModelState = ProfileModelState()

    init {
        intent {
            val currentUser = getCurrentUser.invoke()
            updateModelState {
                copy(
                    user = currentUser
                )
            }
        }
    }
}
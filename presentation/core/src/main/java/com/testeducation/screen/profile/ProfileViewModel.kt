package com.testeducation.screen.profile

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.cases.user.GetUserStatistics
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.profile.ProfileState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileViewModel(
    private val router: NavigationRouter,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<ProfileModelState, ProfileState>,
    errorHandler: IExceptionHandler,
    private val getCurrentUser: GetCurrentUser,
    private val getUserStatistics: GetUserStatistics
) : BaseViewModel<ProfileModelState, ProfileState, ProfileSideEffect>(reducer, errorHandler) {
    override val initialModelState: ProfileModelState = ProfileModelState()

    fun navigateToEdit() {
        router.navigateTo(NavigationScreen.Profile.Editor)
    }

    fun navigateToSupport() {
        router.navigateTo(NavigationScreen.Profile.Support)
    }

    fun navigateAboutApp() {
        router.navigateTo(NavigationScreen.Profile.AboutApp)
    }

    fun navigateToPolicies() {
        router.navigateTo(NavigationScreen.Common.WebView("https://testoria.azurewebsites.net/home/privacy"))
    }

    fun initDataProfile() = intent {
        val currentUser = getCurrentUser.invoke()
        val userStatistics = getUserStatistics.invoke()
        updateModelState {
            copy(
                user = currentUser,
                userStatistics = userStatistics
            )
        }
    }
}
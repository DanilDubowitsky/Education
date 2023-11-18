package com.testeducation.screen.profile

import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.async
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

    init {
        initDataProfile()
    }

    fun navigateToEdit() {
        router.navigateTo(NavigationScreen.Profile.Editor)
    }

    private fun initDataProfile() = intent {
        val currentUser = viewModelScope.async {
            getCurrentUser.invoke()
        }
        val userStatistics = viewModelScope.async {
            getUserStatistics.invoke()
        }
        currentUser.join()
        userStatistics.join()
        val currentUserResult = currentUser.await()
        val userStatisticsResult = userStatistics.await()
        updateModelState {
            copy(
                user = currentUserResult,
                userStatistics = userStatisticsResult
            )
        }
    }
}
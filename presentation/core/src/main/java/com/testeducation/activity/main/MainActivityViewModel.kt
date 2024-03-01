package com.testeducation.activity.main

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.GetTokenExpiration
import com.testeducation.domain.cases.internal.IsAppVersionUpdateRequired
import com.testeducation.domain.interaction.user.UserConfigInteractor
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.activity.MainActivitySideEffect
import com.testeducation.logic.activity.MainActivityState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import kotlinx.coroutines.delay
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

class MainActivityViewModel(
    private val router: NavigationRouter,
    private val userConfigInteractor: UserConfigInteractor,
    private val resourceHelper: IResourceHelper,
    private val isAppVersionUpdateRequired: IsAppVersionUpdateRequired,
    private val getTokenExpiration: GetTokenExpiration,
    reducer: IReducer<MainActivityModelState, MainActivityState>,
    errorHandler: IExceptionHandler
) : BaseViewModel<MainActivityModelState, MainActivityState, MainActivitySideEffect>(
    reducer,
    errorHandler
) {

    override val initialModelState: MainActivityModelState = MainActivityModelState()

    fun prepare() = intent {
        val isUpdateRequired = isAppVersionUpdateRequired()

        if (isUpdateRequired) {
            val title = resourceHelper.extractStringResource(
                StringResource.Update.UpdateRequiredError
            )
            val informationScreen = NavigationScreen.Common.PopUpInformation(
                title
            )
            postSideEffect(MainActivitySideEffect.OnDataLoaded)
            router.navigateTo(informationScreen)
            return@intent
        }

        val isExpired = userConfigInteractor.isRefreshTokenExpired()
        postSideEffect(MainActivitySideEffect.OnDataLoaded)
        val screen = if (isExpired) NavigationScreen.Auth.Login
        else NavigationScreen.Main.Home
        router.replace(screen)

        getTokenExpiration().collect(::handleTokenExpired)
    }

    private fun handleTokenExpired(unit: Unit) = intent {
        userConfigInteractor.setRefreshTokenExpired()
        val screen = NavigationScreen.Auth.Login
        router.replace(screen)
    }

}

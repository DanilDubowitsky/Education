package com.testeducation.screen.profile.edit

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.auth.LogOut
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.ColorResource
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.screen.profile.ProfileSideEffect
import com.testeducation.logic.screen.profile.edit.ProfileEditState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.utils.getColor
import com.testeducation.utils.getString
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileEditViewModel(
    private val router: NavigationRouter,
    private val resourceHelper: IResourceHelper,
    reducer: IReducer<ProfileEditModelState, ProfileEditState>,
    errorHandler: IExceptionHandler,
    private val getCurrentUser: GetCurrentUser,
    private val logOut: LogOut
) : BaseViewModel<ProfileEditModelState, ProfileEditState, ProfileSideEffect>(reducer, errorHandler) {
    override val initialModelState: ProfileEditModelState = ProfileEditModelState()

    init {
        initDataProfile()
    }

    fun back() {
        router.exit()
    }

    fun logOut() {
        intent {
            logOut.invoke()
            router.newRootChain(NavigationScreen.Auth.Login)
        }
    }

    fun goToAvatarChangerScreen() {
        intent {
            val modelState = getModelState()
            val avatarId = modelState.user?.avatarId ?: 0
            router.setResultListener(NavigationScreen.Profile.Avatar.OnAvatarUpdated) { avatarId ->
                intent {
                    val newUser = getModelState().user?.copy(
                        avatarId = avatarId
                    )
                    updateModelState {
                        copy(
                            user = newUser
                        )
                    }
                }

            }
            router.navigateTo(NavigationScreen.Profile.Avatar(avatarId))
        }
    }

    fun deleteAccount() {
        router.setResultListener(NavigationScreen.Common.ConfirmCode.OnConfirm) {
            logOut()
        }
        router.setResultListener(NavigationScreen.Common.ConfirmationBottom.ButtonLeft) {
            router.navigateTo(NavigationScreen.Common.ConfirmCode("", StringResource.Profile.DeleteCodeConfirm.getString(resourceHelper)))
        }
        router.navigateTo(NavigationScreen.Common.ConfirmationBottom(
            title = StringResource.Profile.DeleteConfirmTitle.getString(resourceHelper),
            description = StringResource.Profile.DeleteConfirmDescription.getString(resourceHelper),
            buttonLeft = NavigationScreen.Common.ConfirmationBottom.Button(
                text = StringResource.Common.Delete.getString(resourceHelper),
                color = ColorResource.Main.Red.getColor(resourceHelper)
            ),
            buttonRight = NavigationScreen.Common.ConfirmationBottom.Button(
                text = StringResource.Common.CommonCancel.getString(resourceHelper),
                color = ColorResource.Main.Green.getColor(resourceHelper)
            ),
        ))
    }

    private fun initDataProfile() = intent {
        val currentUser = getCurrentUser.invoke()
        updateModelState {
            copy(
                user = currentUser,
                isLoading = false
            )
        }
    }
}
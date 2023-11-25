package com.testeducation.screen.profile.avatar

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.SetAvatar
import com.testeducation.domain.model.profile.AvatarData
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerSideEffect
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileAvatarChangerViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<ProfileAvatarChangerModelState, ProfileAvatarChangerState>,
    errorHandler: IExceptionHandler,
    private val avatarId: Int,
    private val setAvatar: SetAvatar
) : BaseViewModel<ProfileAvatarChangerModelState, ProfileAvatarChangerState, ProfileAvatarChangerSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: ProfileAvatarChangerModelState =
        ProfileAvatarChangerModelState()

    init {
        intent {
            updateModelState {
                copy(
                    listAvatarId = listOf(
                        AvatarData(
                            0, avatarId == 0
                        ),
                        AvatarData(
                            1, avatarId == 1
                        ),
                        AvatarData(
                            2, avatarId == 2
                        ),
                    )
                )
            }
        }
    }

    fun back() {
        router.exit()
    }

    fun save() {
        intent {
            updateModelState {
                copy(
                    isLoading = true
                )
            }
            val selectedAvatarId = getModelState().listAvatarId.find { it.isSelected }?.id ?: 0
            setAvatar.invoke(selectedAvatarId)
            router.sendResult(
                NavigationScreen.Profile.Avatar.OnAvatarUpdated, selectedAvatarId
            )
            back()
        }
    }

    fun changeAvatarSelected(position: Int) = intent {
        val newList = getModelState().listAvatarId.mapIndexed { index, item ->
            item.copy(
                isSelected = position == index
            )
        }
        updateModelState {
            copy(
                listAvatarId = newList
            )
        }
    }
}
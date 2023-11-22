package com.testeducation.screen.profile.avatar

import com.testeducation.core.BaseViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.model.profile.AvatarData
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerSideEffect
import com.testeducation.logic.screen.profile.avatar.ProfileAvatarChangerState
import com.testeducation.navigation.core.NavigationRouter
import org.orbitmvi.orbit.syntax.simple.intent

class ProfileAvatarChangerViewModel(
    private val router: NavigationRouter,
    reducer: IReducer<ProfileAvatarChangerModelState, ProfileAvatarChangerState>,
    errorHandler: IExceptionHandler,
) : BaseViewModel<ProfileAvatarChangerModelState, ProfileAvatarChangerState, ProfileAvatarChangerSideEffect>(
    reducer,
    errorHandler
) {
    override val initialModelState: ProfileAvatarChangerModelState = ProfileAvatarChangerModelState()

    init {
        intent {
            updateModelState {
                copy(
                    listAvatarId = listOf(
                        AvatarData(
                            0, true
                        ),
                        AvatarData(
                            1, false
                        ),
                        AvatarData(
                            2, false
                        ),
                    )
                )
            }
        }
    }

    fun back() {
        router.exit()
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
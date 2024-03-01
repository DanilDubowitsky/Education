package com.testeducation.education.di.modules.screen.common

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.user.DeleteUser
import com.testeducation.domain.cases.user.DeleteUserConfirm
import com.testeducation.domain.config.user.IConfirmCodeConfig
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.common.confirm.ConfirmCodeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.common.confirm.ConfirmCodeModelState
import com.testeducation.screen.common.confirm.ConfirmCodeReducer
import com.testeducation.screen.common.confirm.ConfirmCodeViewModel
import com.testeducation.ui.screen.common.ConfirmCodeDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ConfirmCodeModule {
    @Binds
    @IntoMap
    @ViewModelKey(ConfirmCodeViewModel::class)
    fun bindViewModel(viewModel: ConfirmCodeViewModel): ViewModel

    companion object {

        @Provides
        fun provideReducer(): IReducer<ConfirmCodeModelState, ConfirmCodeState> =
            ConfirmCodeReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<ConfirmCodeModelState, ConfirmCodeState>,
            exceptionHandler: IExceptionHandler,
            dialog: ConfirmCodeDialog,
            deleteUser: DeleteUser,
            confirmDeleteUser: DeleteUserConfirm,
            confirmCodeConfig: IConfirmCodeConfig,
            router: NavigationRouter
        ): ConfirmCodeViewModel {
            val screen = dialog.getScreen<NavigationScreen.Common.ConfirmCode>()
            return ConfirmCodeViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                title = screen.title,
                description = screen.description,
                deleteUser = deleteUser,
                confirmDeleteUser = confirmDeleteUser,
                confirmCodeConfig = confirmCodeConfig,
                router = router
            )
        }
    }
}
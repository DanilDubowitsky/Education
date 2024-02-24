package com.testeducation.education.di.modules.screen.tests.action

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.action.TestActionState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.action.TestActionModelState
import com.testeducation.screen.tests.action.TestActionReducer
import com.testeducation.screen.tests.action.TestActionViewModel
import com.testeducation.ui.screen.tests.action.TestActionDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestActionModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestActionViewModel::class)
    fun bindViewModel(viewModel: TestActionViewModel): ViewModel

    companion object {

        @Provides
        fun provideReducer(): IReducer<TestActionModelState, TestActionState> =
            TestActionReducer()

        @Provides
        fun provideViewModel(
            dialog: TestActionDialog,
            reducer: IReducer<TestActionModelState, TestActionState>,
            exceptionHandler: IExceptionHandler,
            router: NavigationRouter
        ): TestActionViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.Action>()
            return TestActionViewModel(
                reducer,
                exceptionHandler,
                router,
                screen.testTitle,
                screen.testId
            )
        }
    }
}

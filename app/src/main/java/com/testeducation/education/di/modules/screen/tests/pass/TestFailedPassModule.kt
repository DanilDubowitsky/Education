package com.testeducation.education.di.modules.screen.tests.pass

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.result.failed.TestFailedPassState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.pass.result.failed.TestFailedPassModelState
import com.testeducation.screen.tests.pass.result.failed.TestFailedPassReducer
import com.testeducation.screen.tests.pass.result.failed.TestFailedPassViewModel
import com.testeducation.ui.screen.tests.pass.result.TestFailedPassDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestFailedPassModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestFailedPassViewModel::class)
    fun bindViewModel(viewModel: TestFailedPassViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestFailedPassModelState, TestFailedPassState> =
            TestFailedPassReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<TestFailedPassModelState, TestFailedPassState>,
            exceptionHandler: IExceptionHandler,
            dialog: TestFailedPassDialog,
            router: NavigationRouter
        ): TestFailedPassViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.FailedResult>()
            return TestFailedPassViewModel(
                exceptionHandler,
                reducer,
                router,
                screen.isCheating
            )
        }
    }

}

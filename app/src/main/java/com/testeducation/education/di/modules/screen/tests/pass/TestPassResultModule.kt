package com.testeducation.education.di.modules.screen.tests.pass

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.result.TestPassResultState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.pass.result.TestPassResultModelState
import com.testeducation.screen.tests.pass.result.TestPassResultReducer
import com.testeducation.screen.tests.pass.result.TestPassResultViewModel
import com.testeducation.ui.screen.tests.pass.result.TestPassResultDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestPassResultModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestPassResultViewModel::class)
    fun bindViewModel(viewModel: TestPassResultViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestPassResultModelState, TestPassResultState> =
            TestPassResultReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<TestPassResultModelState, TestPassResultState>,
            exceptionHandler: IExceptionHandler,
            dialog: TestPassResultDialog,
            router: NavigationRouter
        ): TestPassResultViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.Result>()
            return TestPassResultViewModel(
                reducer,
                exceptionHandler,
                router,
                screen.correctAnswers,
                screen.incorrectAnswers,
                screen.isSuccess
            )
        }
    }

}

package com.testeducation.education.di.modules.screen.tests.code.share

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTestCode
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.code.share.TestCodeShareState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.code.share.TestCodeShareModelState
import com.testeducation.screen.tests.code.share.TestCodeShareReducer
import com.testeducation.screen.tests.code.share.TestCodeShareViewModel
import com.testeducation.ui.screen.tests.code.TestCodeShareDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestCodeShareModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestCodeShareViewModel::class)
    fun bindViewModel(viewModel: TestCodeShareViewModel): ViewModel

    companion object {

        @Provides
        fun provideReducer(): IReducer<TestCodeShareModelState, TestCodeShareState> =
            TestCodeShareReducer()

        @Provides
        fun provideViewModel(
            dialog: TestCodeShareDialog,
            reducer: IReducer<TestCodeShareModelState, TestCodeShareState>,
            exceptionHandler: IExceptionHandler,
            router: NavigationRouter,
            getTestCode: GetTestCode
        ): TestCodeShareViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.ShareCode>()

            return TestCodeShareViewModel(
                reducer,
                exceptionHandler,
                router,
                screen.testId,
                getTestCode
            )
        }
    }
}

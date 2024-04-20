package com.testeducation.education.di.modules.screen.tests.edit

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.edit.publish.TestPublishModelState
import com.testeducation.screen.tests.edit.publish.TestPublishReducer
import com.testeducation.screen.tests.edit.publish.TestPublishViewModel
import com.testeducation.ui.screen.tests.edit.publish.TestPublishDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestPublishModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestPublishViewModel::class)
    fun bindViewModel(viewModel: TestPublishViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestPublishModelState, TestPublishState> =
            TestPublishReducer()

        @Provides
        fun provideViewModel(
            dialog: TestPublishDialog,
            reducer: IReducer<TestPublishModelState, TestPublishState>,
            exceptionHandler: IExceptionHandler,
            router: NavigationRouter,
        ): TestPublishViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.TestPublish>()
            return TestPublishViewModel(
                reducer = reducer,
                exceptionHandler = exceptionHandler,
                isPublish = screen.isPublishTest,
                router = router
            )
        }
    }
}
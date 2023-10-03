package com.testeducation.education.di.modules.screen.tests.preview

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.TimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.preview.TestPreviewModelState
import com.testeducation.screen.tests.preview.TestPreviewReducer
import com.testeducation.screen.tests.preview.TestPreviewViewModel
import com.testeducation.ui.screen.tests.preview.TestPreviewFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestPreviewModule {
    @Binds
    @IntoMap
    @ViewModelKey(TestPreviewViewModel::class)
    fun bindViewModel(viewModel: TestPreviewViewModel): ViewModel

    companion object {
        @Provides
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString =
            TimeConverterLongToString(resourceHelper)

        @Provides
        fun provideReducer(
            timeConverter: ITimeConverterLongToString
        ): IReducer<TestPreviewModelState, TestPreviewState> = TestPreviewReducer(timeConverter)

        @Provides
        fun provideViewModel(
            fragment: TestPreviewFragment,
            router: NavigationRouter,
            reducer: IReducer<TestPreviewModelState, TestPreviewState>,
            exceptionHandler: IExceptionHandler,
            getTest: GetTest
        ): TestPreviewViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Preview>()
            return TestPreviewViewModel(
                reducer,
                exceptionHandler,
                router,
                screen.id,
                getTest
            )
        }
    }
}

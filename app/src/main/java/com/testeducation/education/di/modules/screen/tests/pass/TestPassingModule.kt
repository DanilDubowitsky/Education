package com.testeducation.education.di.modules.screen.tests.pass

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetQuestions
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.PassTest
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.answer.AnswerColorExtractor
import com.testeducation.helper.answer.IAnswerColorExtractor
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.TimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.logic.screen.tests.pass.TestPassingState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.list.TestsModelState
import com.testeducation.screen.tests.list.TestsReducer
import com.testeducation.screen.tests.list.TestsViewModel
import com.testeducation.screen.tests.pass.TestPassingModelState
import com.testeducation.screen.tests.pass.TestPassingReducer
import com.testeducation.screen.tests.pass.TestPassingViewModel
import com.testeducation.ui.screen.tests.pass.TestPassingFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestPassingModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestPassingViewModel::class)
    fun bindViewModel(viewModel: TestPassingViewModel): ViewModel

    companion object {
        @Provides
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString =
            TimeConverterLongToString(resourceHelper)

        @Provides
        fun provideAnswerColorExtractor(resourceHelper: IResourceHelper): IAnswerColorExtractor =
            AnswerColorExtractor(resourceHelper)

        @Provides
        fun provideReducer(
            timeConverterLongToString: ITimeConverterLongToString,
            colorExtractor: IAnswerColorExtractor
        ): IReducer<TestPassingModelState, TestPassingState> =
            TestPassingReducer(colorExtractor, timeConverterLongToString)

        @Provides
        fun provideViewModel(
            getTest: GetTest,
            passTest: PassTest,
            router: NavigationRouter,
            reducer: IReducer<TestPassingModelState, TestPassingState>,
            exceptionHandler: IExceptionHandler,
            fragment: TestPassingFragment,
            getQuestions: GetQuestions,
            getCurrentUser: GetCurrentUser
        ): TestPassingViewModel {
            val testId = fragment.getScreen<NavigationScreen.Tests.Passing>().id
            return TestPassingViewModel(
                reducer,
                exceptionHandler,
                router,
                testId,
                getTest,
                passTest,
                getQuestions,
                getCurrentUser
            )
        }
    }

}

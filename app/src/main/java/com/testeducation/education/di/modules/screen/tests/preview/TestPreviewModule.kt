package com.testeducation.education.di.modules.screen.tests.preview

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTest
import com.testeducation.domain.cases.test.GetTestByCode
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.avatar.AvatarHelper
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
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
        fun provideTestHelper(toggleTestLike: ToggleTestLike): ITestHelper =
            TestHelper(toggleTestLike)

        @Provides
        fun profileAvatarHelper(resourceHelper: IResourceHelper): IAvatarHelper =
            AvatarHelper(resourceHelper)

        @Provides
        fun provideReducer(avatarHelper: IAvatarHelper): IReducer<TestPreviewModelState, TestPreviewState> =
            TestPreviewReducer(avatarHelper)

        @Provides
        fun provideViewModel(
            fragment: TestPreviewFragment,
            router: NavigationRouter,
            reducer: IReducer<TestPreviewModelState, TestPreviewState>,
            exceptionHandler: IExceptionHandler,
            getTest: GetTest,
            likeTest: ToggleTestLike,
            getTests: GetTests,
            testHelper: ITestHelper,
            getTestByCode: GetTestByCode,
            getCurrentUser: GetCurrentUser
        ): TestPreviewViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Preview>()
            return TestPreviewViewModel(
                reducer,
                exceptionHandler,
                testHelper,
                router,
                screen.id,
                screen.code,
                getTest,
                likeTest,
                getTests,
                getTestByCode,
                getCurrentUser
            )
        }
    }
}

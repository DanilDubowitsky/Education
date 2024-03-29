package com.testeducation.education.di.modules.screen.tests.list

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.domain.cases.user.GetCurrentUser
import com.testeducation.domain.cases.user.IsVisibleAvatar
import com.testeducation.domain.cases.user.SetVisibleAvatar
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.avatar.AvatarHelper
import com.testeducation.helper.avatar.IAvatarHelper
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.list.TestsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.list.TestsModelState
import com.testeducation.screen.tests.list.TestsReducer
import com.testeducation.screen.tests.list.TestsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestsViewModel::class)
    fun bindViewModel(viewModel: TestsViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(avatarHelper: IAvatarHelper): IReducer<TestsModelState, TestsState> =
            TestsReducer(avatarHelper)

        @Provides
        fun profileAvatarHelper(resourceHelper: IResourceHelper): IAvatarHelper =
            AvatarHelper(resourceHelper)

        @Provides
        fun provideTestHelper(
            toggleTestLike: ToggleTestLike
        ): ITestHelper = TestHelper(toggleTestLike)

        @Provides
        fun provideViewModel(
            getTests: GetTests,
            router: NavigationRouter,
            getCurrentUser: GetCurrentUser,
            getThemes: GetThemes,
            testHelper: ITestHelper,
            reducer: IReducer<TestsModelState, TestsState>,
            exceptionHandler: IExceptionHandler,
            isVisibleAvatar: IsVisibleAvatar,
            setVisibleAvatar: SetVisibleAvatar
        ): TestsViewModel {
            return TestsViewModel(
                router,
                getTests,
                getThemes,
                getCurrentUser,
                testHelper,
                isVisibleAvatar,
                setVisibleAvatar,
                reducer,
                exceptionHandler,
            )
        }
    }
}
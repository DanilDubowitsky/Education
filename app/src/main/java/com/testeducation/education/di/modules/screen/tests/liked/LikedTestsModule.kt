package com.testeducation.education.di.modules.screen.tests.liked

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.test.GetTests
import com.testeducation.domain.cases.test.ToggleTestLike
import com.testeducation.domain.cases.theme.GetThemes
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.test.ITestHelper
import com.testeducation.helper.test.TestHelper
import com.testeducation.logic.screen.tests.liked.LikedTestsState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.screen.tests.liked.LikedTestsModelState
import com.testeducation.screen.tests.liked.LikedTestsReducer
import com.testeducation.screen.tests.liked.LikedTestsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface LikedTestsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LikedTestsViewModel::class)
    fun bindViewModel(viewModel: LikedTestsViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<LikedTestsModelState, LikedTestsState> =
            LikedTestsReducer()

        @Provides
        fun provideTestHelper(
            toggleTestLike: ToggleTestLike
        ): ITestHelper = TestHelper(toggleTestLike)

        @Provides
        fun provideViewModel(
            router: NavigationRouter,
            testShortHelper: ITestHelper,
            getLikedTests: GetTests,
            getThemes: GetThemes,
            reducer: IReducer<LikedTestsModelState, LikedTestsState>,
            exceptionHandler: IExceptionHandler
        ): LikedTestsViewModel {
            return LikedTestsViewModel(
                router,
                testShortHelper,
                getThemes,
                getLikedTests,
                reducer,
                exceptionHandler
            )
        }
    }

}

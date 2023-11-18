package com.testeducation.education.di.modules.screen.tests.statistic

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.domain.cases.question.GetTestPassStatistic
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.statistic.TestPassStatisticState
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.statistic.TestPassStatisticModelState
import com.testeducation.screen.tests.statistic.TestPassStatisticReducer
import com.testeducation.screen.tests.statistic.TestPassStatisticViewModel
import com.testeducation.ui.screen.tests.statistic.TestPassStatisticFragment
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TestPassStatisticModule {

    @Binds
    @IntoMap
    @ViewModelKey(TestPassStatisticViewModel::class)
    fun bindViewModel(viewModel: TestPassStatisticViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<TestPassStatisticModelState, TestPassStatisticState> =
            TestPassStatisticReducer()

        @Provides
        fun provideViewModel(
            fragment: TestPassStatisticFragment,
            getTestPassStatistic: GetTestPassStatistic,
            exceptionHandler: IExceptionHandler,
            reducer: IReducer<TestPassStatisticModelState, TestPassStatisticState>
        ): TestPassStatisticViewModel {
            val screen = fragment.getScreen<NavigationScreen.Tests.Statistic>()
            return TestPassStatisticViewModel(
                reducer,
                exceptionHandler,
                screen.testId,
                getTestPassStatistic
            )
        }
    }
}

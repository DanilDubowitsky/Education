package com.testeducation.education.di.modules.screen.tests.pass

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.pass.answer.FullAnswerState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.pass.answer.FullAnswerModelState
import com.testeducation.screen.tests.pass.answer.FullAnswerReducer
import com.testeducation.screen.tests.pass.answer.FullAnswerViewModel
import com.testeducation.ui.screen.tests.pass.answer.FullAnswerTextDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface FullAnswerTextModule {
    @Binds
    @IntoMap
    @ViewModelKey(FullAnswerViewModel::class)
    fun bindViewModel(viewModel: FullAnswerViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<FullAnswerModelState, FullAnswerState> =
            FullAnswerReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<FullAnswerModelState, FullAnswerState>,
            exceptionHandler: IExceptionHandler,
            dialog: FullAnswerTextDialog
        ): FullAnswerViewModel {
            val screen = dialog.getScreen<NavigationScreen.Tests.FullAnswer>()
            return FullAnswerViewModel(
                reducer,
                exceptionHandler,
                screen.text
            )
        }
    }
}

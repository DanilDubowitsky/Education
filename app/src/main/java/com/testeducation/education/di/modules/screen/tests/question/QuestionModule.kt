package com.testeducation.education.di.modules.screen.tests.question

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.logic.screen.tests.creation.question.SelectionQuestionTypeState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeModelState
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeModelStateReducer
import com.testeducation.screen.tests.creation.question.SelectionQuestionTypeViewModel
import com.testeducation.ui.screen.tests.creation.SelectionQuestionTypeDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface QuestionModule {
    @Binds
    @IntoMap
    @ViewModelKey(SelectionQuestionTypeViewModel::class)
    fun bindViewModel(viewModel: SelectionQuestionTypeViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(): IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState> =
            SelectionQuestionTypeModelStateReducer()

        @Provides
        fun provideViewModel(
            reducer: IReducer<SelectionQuestionTypeModelState, SelectionQuestionTypeState>,
            exceptionHandler: IExceptionHandler,
            navigationRouter: NavigationRouter,
            dialog: SelectionQuestionTypeDialog
        ): SelectionQuestionTypeViewModel {
            val screen = dialog.getScreen<NavigationScreen.Main.SelectionTest>()
            return SelectionQuestionTypeViewModel(
                reducer = reducer,
                errorHandler = exceptionHandler,
                router = navigationRouter,
                idTest = screen.idTest
            )
        }
    }

}
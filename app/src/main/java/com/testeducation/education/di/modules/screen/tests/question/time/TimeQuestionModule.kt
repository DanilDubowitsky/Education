package com.testeducation.education.di.modules.screen.tests.question.time

import androidx.lifecycle.ViewModel
import com.testeducation.core.IReducer
import com.testeducation.education.di.viewmodel.ViewModelKey
import com.testeducation.helper.error.IExceptionHandler
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.question.TimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.logic.screen.tests.creation.question.creation.time.TimeQuestionState
import com.testeducation.navigation.core.NavigationRouter
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.screen.tests.creation.question.creation.time.TimeQuestionModelState
import com.testeducation.screen.tests.creation.question.creation.time.TimeQuestionReducer
import com.testeducation.screen.tests.creation.question.creation.time.TimeQuestionViewModel
import com.testeducation.ui.screen.tests.creation.time.TimeQuestionDialog
import com.testeducation.ui.utils.getScreen
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface TimeQuestionModule {
    @Binds
    @IntoMap
    @ViewModelKey(TimeQuestionViewModel::class)
    fun bindViewModel(viewModel: TimeQuestionViewModel): ViewModel

    companion object {
        @Provides
        fun provideReducer(timeConverterLongToString: ITimeConverterLongToString): IReducer<TimeQuestionModelState, TimeQuestionState> =
            TimeQuestionReducer(timeConverterLongToString)

        @Provides
        fun questionTimeConverter(resourceHelper: IResourceHelper): ITimeConverterLongToString {
            return TimeConverterLongToString(resourceHelper)
        }

        @Provides
        fun provideViewModel(
            reducer: IReducer<TimeQuestionModelState, TimeQuestionState>,
            exceptionHandler: IExceptionHandler,
            navigationRouter: NavigationRouter,
            dialog: TimeQuestionDialog
        ): TimeQuestionViewModel {
            val screen = dialog.getScreen<NavigationScreen.QuestionCreation.TimeQuestion>()
            return TimeQuestionViewModel(
                reducer = reducer,
                errorHandler = exceptionHandler,
                router = navigationRouter,
                currentTimeQuestion = screen.time
            )
        }
    }

}
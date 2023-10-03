package com.testeducation.screen.tests.preview

import com.testeducation.converter.test.question.toUi
import com.testeducation.core.IReducer
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.utils.DAY_MONTH_YEAR_FULL
import com.testeducation.utils.formatDate

class TestPreviewReducer(
    private val timeConverter: ITimeConverterLongToString
) : IReducer<TestPreviewModelState, TestPreviewState> {

    override fun reduce(modelState: TestPreviewModelState): TestPreviewState {

        return TestPreviewState(
            isLoading = modelState.loadingState == TestPreviewModelState.LoadingState.LOADING,
            theme = modelState.test?.theme?.title.orEmpty(),
            title = modelState.test?.title.orEmpty(),
            formatDate(DAY_MONTH_YEAR_FULL, modelState.test?.creationDate ?: 0L),
            isLiked = modelState.test?.liked ?: false,
            allowPreviewQuestions = modelState.test?.settings?.previewQuestions ?: false,
            description = "",
            questions = modelState.test?.questions?.toUi(timeConverter) ?: emptyList()
        )
    }
}

package com.testeducation.screen.tests.preview

import com.testeducation.converter.test.toUIModels
import com.testeducation.core.IReducer
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.utils.DAY_MONTH_YEAR_FULL
import com.testeducation.utils.formatDateInSeconds
import com.testeducation.utils.getElapsedTime

class TestPreviewReducer : IReducer<TestPreviewModelState, TestPreviewState> {

    override fun reduce(modelState: TestPreviewModelState): TestPreviewState = modelState.run {
        val tempDesc = ""

        val timeLimit = test?.settings?.timeLimit ?: 0

        TestPreviewState(
            isLoading = loadingState == TestPreviewModelState.LoadingState.LOADING,
            theme = test?.theme?.title.orEmpty(),
            title = test?.title.orEmpty(),
            formatDateInSeconds(DAY_MONTH_YEAR_FULL, test?.creationDate ?: 0L),
            isLiked = test?.liked ?: false,
            allowPreviewQuestions = test?.settings?.previewQuestions ?: false,
            description = tempDesc,
            questionsCount = 0,
            creatorName = test?.creator?.username.orEmpty(),
            isExpandButtonVisible = tempDesc.length >= MAX_DESCRIPTION_LENGTH,
            isExpand = isDescriptionExpanded,
            timeLimit = getElapsedTime(timeLimit),
            authorTests = authorTests.toUIModels(),
            hideTestTimeLimit = timeLimit == 0
        )
    }

    private companion object {
        const val MAX_DESCRIPTION_LENGTH = 250
    }
}

package com.testeducation.screen.tests.preview

import com.testeducation.converter.test.question.toUi
import com.testeducation.core.IReducer
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.logic.screen.tests.preview.TestPreviewState
import com.testeducation.utils.DAY_MONTH_YEAR_FULL
import com.testeducation.utils.formatDateInSeconds

class TestPreviewReducer(
    private val timeConverter: ITimeConverterLongToString
) : IReducer<TestPreviewModelState, TestPreviewState> {

    override fun reduce(modelState: TestPreviewModelState): TestPreviewState = modelState.run {
        val tempDesc = "j ajksdk ajkdka jdkaj jaskjskjdaskdjskadjaskdjaskldajsdkajdasdkaldkasldkasldkasdajdkjadasdh jahdjashdajsdjasdas;ldkkflsdjfkjjahdjaksh ajdhajdhjfhsdfjjasdkajsdkjfshdfjkha" +
                "jkhasjdhajkdas kjfskdhfjsdhfsjfhjasdjahdjkahf akdlasdk aksdaj akjdkajsdk jaskdjakld jakldjaiu aidjaklsdjaыkkakskallkda"

        TestPreviewState(
            isLoading = loadingState == TestPreviewModelState.LoadingState.LOADING,
            theme = test?.theme?.title.orEmpty(),
            title = test?.title.orEmpty(),
            formatDateInSeconds(DAY_MONTH_YEAR_FULL, test?.creationDate ?: 0L),
            isLiked = test?.liked ?: false,
            allowPreviewQuestions = test?.settings?.previewQuestions ?: false,
            description = tempDesc,
            questions = test?.questions?.toUi(timeConverter) ?: emptyList(),
            isQuestionsShown = isQuestionsShown,
            creatorName = test?.creator?.username.orEmpty(),
            isExpandButtonVisible = tempDesc.length >= MAX_DESCRIPTION_LENGTH,
            isExpand = isDescriptionExpanded
        )
    }

    private companion object {
        const val MAX_DESCRIPTION_LENGTH = 250
    }
}

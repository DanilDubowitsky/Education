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

    override fun reduce(modelState: TestPreviewModelState): TestPreviewState {

        return TestPreviewState(
            isLoading = modelState.loadingState == TestPreviewModelState.LoadingState.LOADING,
            theme = modelState.test?.theme?.title.orEmpty(),
            title = modelState.test?.title.orEmpty(),
            formatDateInSeconds(DAY_MONTH_YEAR_FULL, modelState.test?.creationDate ?: 0L),
            isLiked = modelState.test?.liked ?: false,
            allowPreviewQuestions = modelState.test?.settings?.previewQuestions ?: false,
            description = "j ajksdk ajkdka jdkaj jaskjskjdaskdjskadjaskdjaskldajsdkajdasdkaldkasldkasldkasdajdkjadasdh jahdjashdajsdjasdas;ldkkflsdjfkjjahdjaksh ajdhajdhjfhsdfjjasdkajsdkjfshdfjkha " +
                    "jkhasjdhajkdas kjfskdhfjsdhfsjfhjasdjahdjkahf akdlasdk aksdaj akjdkajsdk jaskdjakld jakldjaiu aidjaklsdjakdjjkhjh jhagdhagd h ajdklajdjhgfjkh jahdyuaghd jh jahdja hgjhjghjagdhjagdakjhdsjk h",
            questions = modelState.test?.questions?.toUi(timeConverter) ?: emptyList(),
            isQuestionsShown = modelState.isQuestionsShown
        )
    }
}

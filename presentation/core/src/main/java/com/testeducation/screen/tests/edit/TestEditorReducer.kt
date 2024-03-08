package com.testeducation.screen.tests.edit

import com.testeducation.converter.test.question.toUi
import com.testeducation.converter.test.toUI
import com.testeducation.core.IReducer
import com.testeducation.domain.model.test.Test
import com.testeducation.helper.question.ITimeConverterLongToString
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.helper.resource.StringResource
import com.testeducation.logic.model.test.CardTestStyle
import com.testeducation.logic.model.test.TestDetailsUi
import com.testeducation.logic.model.test.TestStyleUi
import com.testeducation.logic.screen.tests.edit.TestEditorState
import com.testeducation.utils.getString

class TestEditorReducer(
    private val timeConverterLongToString: ITimeConverterLongToString,
    private val resource: IResourceHelper
) :
    IReducer<TestEditorModelState, TestEditorState> {
    override fun reduce(modelState: TestEditorModelState): TestEditorState {
        val test = modelState.test
        return if (test == null) {
            TestEditorState.NoInit
        } else TestEditorState.Content(
            testDetails = TestDetailsUi(
                id = test.id,
                title = test.title,
                style = TestStyleUi(
                    color = test.style.color,
                    background = CardTestStyle.valueOf(test.style.background)
                ),
                settings = test.settings.toUI(),
                questions = emptyList(),
                status = test.status.name,
                likes = test.likes,
                liked = test.liked,
                passesUser = test.passesUser,
                passed = test.passed,
                theme = test.theme.toUI()
            ),
            questionDetailsUi = modelState.questionDetails.toUi(timeConverterLongToString),
            visibleLoadingPublish = modelState.loadingPublish,
            btnPublishText = if (test.status == Test.Status.PUBLISHED) {
                StringResource.Common.CommonSave.getString(resource)
            } else {
                StringResource.Test.PublishTitle.getString(resource)
            },
            titleCountQuestion = StringResource.Test.QuestionCountTitle(modelState.questionDetails.size - 1).getString(resource),
            isRefreshing = modelState.isRefreshing
        )
    }
}
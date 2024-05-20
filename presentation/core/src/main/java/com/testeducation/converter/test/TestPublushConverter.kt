package com.testeducation.converter.test

import com.testeducation.logic.screen.tests.edit.TestPublishState
import com.testeducation.screen.tests.edit.publish.TestPublishModelState

fun TestPublishModelState.StatusPublish?.convertToUi() = when (this) {
    TestPublishModelState.StatusPublish.PUBLISH -> TestPublishState.StatusPublish.PUBLISH
    TestPublishModelState.StatusPublish.SCHEDULED -> TestPublishState.StatusPublish.SCHEDULED
    else -> TestPublishState.StatusPublish.DRAFT
}
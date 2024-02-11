package com.testeducation.logic.screen.tests.preview

import com.testeducation.logic.model.test.TestShortUI

data class TestPreviewState(
    val isLoading: Boolean,
    val theme: String,
    val title: String,
    val createdDate: String,
    val isLiked: Boolean,
    val description: String,
    val questionsCount: Int,
    val allowPreviewQuestions: Boolean,
    val creatorName: String,
    val isExpandButtonVisible: Boolean,
    val isExpand: Boolean,
    val timeLimit: String,
    val authorTests: List<TestShortUI>,
    val hideTestTimeLimit: Boolean,
    val avatarResource: Int,
    val isPassVisible: Boolean,
    val isShareAvailable: Boolean
)

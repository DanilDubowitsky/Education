package com.testeducation.remote.converter.test

import com.testeducation.domain.model.test.TestAvailability
import com.testeducation.domain.model.test.TestQuestionOrder
import com.testeducation.domain.model.test.TestSettingsItem
import com.testeducation.domain.model.test.TestShowResultMode
import com.testeducation.remote.model.test.RemoteTestAvailability
import com.testeducation.remote.model.test.RemoteTestQuestionOrder
import com.testeducation.remote.model.test.RemoteTestSettingsItem
import com.testeducation.remote.model.test.RemoteTestShowResultMode

fun RemoteTestSettingsItem.toModels(): TestSettingsItem {
    return TestSettingsItem(
        availability = this.availability.toModels(),
        testQuestionOrder = this.testQuestionOrder.toModels(),
        showResultMode = this.showResultMode.toModels(),
        minCorrectAnswer = this.minCorrectAnswer,
        allowPreviewQuestions = this.allowPreviewQuestions,
        antiCheating = this.antiCheating
    )
}

private fun RemoteTestAvailability.toModels() = when (this) {
    RemoteTestAvailability.Public -> TestAvailability.Public
    else -> TestAvailability.Private
}

private fun RemoteTestQuestionOrder.toModels() = when (this) {
    RemoteTestQuestionOrder.Sequencial -> TestQuestionOrder.Sequencial
    else -> TestQuestionOrder.Shuffled
}

private fun RemoteTestShowResultMode.toModels() = when (this) {
    RemoteTestShowResultMode.AterPass -> TestShowResultMode.AfterPass
    else -> TestShowResultMode.Immediately
}
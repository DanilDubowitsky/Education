package com.testeducation.screen.tests.edit.publish

import java.util.Calendar

data class TestPublishModelState(
    val status: StatusPublish? = null,
    val calendar: Calendar = Calendar.getInstance(),
) {
    enum class StatusPublish {
        PUBLISH, DRAFT, SCHEDULED
    }
}
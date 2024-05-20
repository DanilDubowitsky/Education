package com.testeducation.logic.screen.tests.edit

import java.util.Calendar

data class TestPublishState(
    val statusPublish: StatusPublish?,
    val calendar: Calendar,
) {
    enum class StatusPublish {
        PUBLISH, DRAFT, SCHEDULED;
    }
}
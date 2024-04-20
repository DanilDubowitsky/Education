package com.testeducation.logic.screen.tests.edit

data class TestPublishState(
    val statusPublish: StatusPublish?
) {
    enum class StatusPublish {
        PUBLISH, DRAFT;
    }
}
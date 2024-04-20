package com.testeducation.screen.tests.edit.publish

data class TestPublishModelState(val status: StatusPublish? = null) {
    enum class StatusPublish {
        PUBLISH, DRAFT
    }
}
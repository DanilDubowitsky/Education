package com.testeducation.remote.model.test

import com.google.gson.annotations.SerializedName
import com.testeducation.remote.model.question.RemoteQuestion
import com.testeducation.remote.model.user.RemoteUserInfo

data class RemoteTest(
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("style")
    val style: RemoteTestStyle,
    @SerializedName("settings")
    val settings: RemoteTestSettings,
    @SerializedName("status")
    val status: Status,
    @SerializedName("likes")
    val likes: Long,
    @SerializedName("liked")
    val liked: Boolean,
    @SerializedName("passes")
    val passesUser: Long,
    @SerializedName("passed")
    val passed: Boolean,
    @SerializedName("theme")
    val theme: RemoteThemeShort,
    @SerializedName("date_created")
    val creationDate: Long,
    @SerializedName("owner")
    val creator: RemoteUserInfo,
    @SerializedName("questions_count")
    val questionsCount: Int
) {

    enum class Status {
        Draft,
        Scheduled,
        Published,
        Locked
    }
}

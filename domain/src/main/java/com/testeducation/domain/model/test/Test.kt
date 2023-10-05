package com.testeducation.domain.model.test

import com.testeducation.domain.model.question.Question
import com.testeducation.domain.model.theme.ThemeShort
import com.testeducation.domain.model.user.UserInfo

data class Test(
    val id: String,
    val title: String,
    val style: TestStyle,
    val settings: TestSettings,
    val questions: List<Question>,
    val status: Status,
    val likes: Long,
    val liked: Boolean,
    val passesUser: Long,
    val passed: Boolean,
    val theme: ThemeShort,
    val creationDate: Long,
    val creator: UserInfo
) {

    enum class Status {
        DRAFT,
        SCHEDULED,
        PUBLISHED,
        LOCKED
    }
}

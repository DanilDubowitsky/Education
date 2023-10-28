package com.testeducation.helper.resource

sealed interface AnswerColorResource : ColorResource {
    object Blue : AnswerColorResource
    object Red : AnswerColorResource
    object Green : AnswerColorResource
    object Orange : AnswerColorResource

    object Blue60 : AnswerColorResource
    object Red60 : AnswerColorResource
    object Green60 : AnswerColorResource
    object Orange60 : AnswerColorResource
}

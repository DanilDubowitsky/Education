package com.testeducation.helper.resource

sealed interface DrawableResource {
    object MatchIconQuestion : DrawableResource
    object DefaultIconQuestion : DrawableResource
    object WriteAnswerIconQuestion : DrawableResource
    object OrderAnswerIconQuestion : DrawableResource
}
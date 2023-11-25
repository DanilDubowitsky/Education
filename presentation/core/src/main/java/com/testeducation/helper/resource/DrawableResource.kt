package com.testeducation.helper.resource

sealed interface DrawableResource {
    object MatchIconQuestion : DrawableResource
    object DefaultIconQuestion : DrawableResource
    object WriteAnswerIconQuestion : DrawableResource
    object OrderAnswerIconQuestion : DrawableResource

    sealed interface Avatar : DrawableResource {
        object First : Avatar
        object Second : Avatar
        object Default : Avatar
    }
}
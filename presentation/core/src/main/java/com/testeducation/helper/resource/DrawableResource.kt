package com.testeducation.helper.resource

sealed interface DrawableResource {
    object MatchIconQuestion : DrawableResource
    object DefaultIconQuestion : DrawableResource
    object WriteAnswerIconQuestion : DrawableResource
    object OrderAnswerIconQuestion : DrawableResource

    sealed interface Avatar : DrawableResource {
        object First : Avatar
        object Second : Avatar
        object Three : Avatar
        object Four : Avatar
        object Five : Avatar
        object Six : Avatar
        object Seven : Avatar
        object Eight : Avatar
        object Default : Avatar
    }
}
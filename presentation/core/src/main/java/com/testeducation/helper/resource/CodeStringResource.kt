package com.testeducation.helper.resource

sealed interface CodeStringResource : StringResource {

    object CodeError : CodeStringResource

}
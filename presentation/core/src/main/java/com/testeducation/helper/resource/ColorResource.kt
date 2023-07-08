package com.testeducation.helper.resource

sealed interface ColorResource {
    sealed interface Main : ColorResource {
        object Blue : Main
        object Red : Main
        object Green : Main
        object Orange: Main
    }
}
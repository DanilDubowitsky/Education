package com.testeducation.helper.resource

interface IResourceHelper {
    fun extractStringResource(resource: StringResource): String
    fun extractColorResource(resource: ColorResource): Int
}

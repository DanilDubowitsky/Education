package com.testeducation.utils

object StringUtils {
    fun String.isNotEmptyOrBlank() = this.isNotEmpty() && this.isNotBlank()

    fun String.isEmptyOrBlank() = !this.isNotEmptyOrBlank()

    fun String?.orDefault(default: String)  = this ?: default
}
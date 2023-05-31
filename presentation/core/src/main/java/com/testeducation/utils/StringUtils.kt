package com.testeducation.utils

object StringUtils {
    fun String.isNotEmptyBlank() = this.isNotEmpty() && this.isNotBlank()

    fun String.isEmptyBlank() = !this.isNotEmptyBlank()

    fun String?.orDefault(default: String)  = this ?: default
}
package com.testeducation.utils

fun <T, R> List<T>.firstByConditionOrNull(
    condition: T.() -> R,
    value: R
): T? {
    return firstOrNull {
        it.condition() == value
    }
}

fun <T, R> List<T>.indexOfByConditionOrNull(
    condition: T.() -> R,
    value: R
): Int? {
    return indexOfFirst {
        it.condition() == value
    }.takeIf { it != -1 }
}

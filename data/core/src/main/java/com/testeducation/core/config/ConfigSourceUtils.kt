package com.testeducation.core.config

import com.testeducation.domain.utils.MINUTE_IN_MILLIS

const val DEFAULT_UPDATE_MEDIUM_TTL = 10 * MINUTE_IN_MILLIS

suspend fun <T> IConfigSource.updateAndRead(
    onUpdate: suspend () -> Unit,
    updateTTL: Long = DEFAULT_UPDATE_MEDIUM_TTL,
    onRead: suspend () -> T
): T {
    val lastUpdateTime = getLong(LAST_UPDATE_TIME_KEY,)
    val currentTime = System.currentTimeMillis()

    if ((currentTime - updateTTL) > lastUpdateTime) {
        setLong(LAST_UPDATE_TIME_KEY, System.currentTimeMillis())
        onUpdate()
    }
    return onRead()
}

private const val LAST_UPDATE_TIME_KEY = "LAST_UPDATE_TIME"

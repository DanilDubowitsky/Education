package com.testeducation.ui.helper

import android.os.Handler
import android.os.Looper

class TimeHandler {

    private val handler = Handler(Looper.getMainLooper())
    private val onUpdateListeners: HashMap<Any?, (Long) -> Unit> = HashMap()
    private val onExpireListeners: HashMap<Any?, () -> Unit> = HashMap()
    private val currentTimes: HashMap<Any?, Long> = HashMap()

    fun start(time: Long, interval: Long, key: Any? = null) {
        currentTimes[key] = time
        startInternal(key, time, interval)
    }

    fun stop(key: Any? = null) {
        handler.removeCallbacksAndMessages(key)
    }

    fun getRemainingTime(key: Any? = null): Long = currentTimes[key] ?: 0L

    fun setOnUpdateListener(key: Any? = null, listener: (Long) -> Unit) {
        onUpdateListeners[key] = listener
    }

    fun setOnExpireListener(key: Any? = null, listener: () -> Unit) {
        onExpireListeners[key] = listener
    }

    fun release(key: Any? = null) {
        handler.removeCallbacksAndMessages(key)
        onExpireListeners.clear()
        onUpdateListeners.clear()
    }

    fun releaseAll(vararg keys: Any?) {
        keys.forEach(handler::removeCallbacksAndMessages)
        onExpireListeners.clear()
        onUpdateListeners.clear()
    }

    private fun startInternal(key: Any?, time: Long, interval: Long) {
        if (time < 0L) {
            onExpireListeners[key]?.invoke()
            return
        }
        handler.postDelayed({
            val remainingTime = time - interval
            currentTimes[key] = remainingTime
            onUpdateListeners[key]?.invoke(time)
            startInternal(key, remainingTime, interval)
        }, interval)
    }

}

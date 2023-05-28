package com.testeducation.navigation.core

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread

class NavigationHost {

    private val mainHandler: Handler = Handler(Looper.getMainLooper())
    private val navigators: HashMap<String?, Navigator> = hashMapOf()

    @MainThread
    fun setNavigator(navigator: Navigator, key: String? = null) {
        navigators[key] = navigator
    }

    @MainThread
    fun removeNavigator(key: String?) {
        navigators.remove(key)
    }

    fun executeCommand(command: Command, key: String?) {
        mainHandler.post {
            navigators[key]?.executeCommand(command)
        }
    }

}

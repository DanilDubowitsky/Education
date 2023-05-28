package com.example.navigation.core

import com.example.navigation.screen.NavigationScreen

interface NavigationRouter : IResultWire {
    fun navigateTo(screen: NavigationScreen, key: String? = null)
    fun exit(key: String? = null)
    fun replace(screen: NavigationScreen, key: String? = null)
}

package com.example.navigation.core

import com.example.navigation.screen.NavigationScreen

interface IScreenAdapter {
    fun createPlatformScreen(screen: NavigationScreen): Screen
}

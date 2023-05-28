package com.testeducation.navigation.core

import com.testeducation.navigation.screen.NavigationScreen

interface IScreenAdapter {
    fun createPlatformScreen(screen: NavigationScreen): Screen
}

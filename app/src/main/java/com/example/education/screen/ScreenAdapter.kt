package com.example.education.screen

import com.example.navigation.core.IScreenAdapter
import com.example.navigation.core.Screen
import com.example.navigation.screen.NavigationScreen
import com.example.ui.screen.auth.login.LoginFragment
import com.example.ui.screen.auth.registration.RegistrationFragment

class ScreenAdapter : IScreenAdapter {

    override fun createPlatformScreen(screen: NavigationScreen): Screen =
        when (screen) {
            is NavigationScreen.Auth -> createPlatformScreen(screen)
        }

    private fun createPlatformScreen(screen: NavigationScreen.Auth): Screen =
        when (screen) {
            NavigationScreen.Auth.Login -> Screen.FragmentScreen {
                LoginFragment()
            }
            NavigationScreen.Auth.Registration -> Screen.FragmentScreen {
                RegistrationFragment()
            }

        }
}

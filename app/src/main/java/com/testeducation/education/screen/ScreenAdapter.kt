package com.testeducation.education.screen

import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.Screen
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.ui.screen.auth.confirmation.EmailConfirmationFragment
import com.testeducation.ui.screen.auth.login.LoginFragment
import com.testeducation.ui.screen.auth.registration.RegistrationFragment
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.testeducation.ui.screen.home.FragmentHome
import com.testeducation.ui.screen.tests.TestsFragment
import com.testeducation.ui.utils.withScreen

class ScreenAdapter : IScreenAdapter {

    override fun createPlatformScreen(screen: NavigationScreen): Screen =
        when (screen) {
            is NavigationScreen.Auth -> createPlatformScreen(screen)
            is NavigationScreen.Common -> createPlatformScreen(screen)
            is NavigationScreen.Main -> createPlatformScreen(screen)
        }

    private fun createPlatformScreen(screen: NavigationScreen.Auth): Screen =
        when (screen) {

            NavigationScreen.Auth.Login -> Screen.FragmentScreen {
                LoginFragment()
            }

            NavigationScreen.Auth.Registration -> Screen.FragmentScreen {
                RegistrationFragment()
            }

            NavigationScreen.Auth.EmailConfirmation -> Screen.FragmentScreen {
                EmailConfirmationFragment()
            }
        }

    private fun createPlatformScreen(screen: NavigationScreen.Common): Screen =
        when (screen) {

            is NavigationScreen.Common.Confirmation -> Screen.DialogScreen {
                ConfirmationDialog().withScreen(screen)
            }

            is NavigationScreen.Common.Information -> Screen.DialogScreen {
                InformationDialog().withScreen(screen)
            }
        }

    private fun createPlatformScreen(screen: NavigationScreen.Main): Screen = when (screen) {

        NavigationScreen.Main.Home -> Screen.FragmentScreen {
            FragmentHome()
        }

        NavigationScreen.Main.Tests -> Screen.FragmentScreen {
            TestsFragment()
        }

        NavigationScreen.Main.CreationTest -> Screen.DialogScreen {
            CreationTestDialogFragment().withScreen(screen)
        }
    }
}
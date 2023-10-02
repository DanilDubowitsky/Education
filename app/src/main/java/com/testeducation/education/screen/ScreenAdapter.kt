package com.testeducation.education.screen

import com.testeducation.navigation.core.IScreenAdapter
import com.testeducation.navigation.core.Screen
import com.testeducation.navigation.screen.NavigationScreen
import com.testeducation.ui.screen.auth.confirmation.CodeConfirmationFragment
import com.testeducation.ui.screen.auth.login.LoginFragment
import com.testeducation.ui.screen.auth.registration.RegistrationFragment
import com.testeducation.ui.screen.auth.reset.email.PasswordResetEmailFragment
import com.testeducation.ui.screen.auth.reset.password.NewPasswordFragment
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationAlertDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.testeducation.ui.screen.home.FragmentHome
import com.testeducation.ui.screen.home.library.TestLibraryFragment
import com.testeducation.ui.screen.tests.creation.CreationTestDialogFragment
import com.testeducation.ui.screen.tests.creation.QuestionCreationFragment
import com.testeducation.ui.screen.tests.creation.SelectionQuestionTypeDialog
import com.testeducation.ui.screen.tests.edit.TestEditorFragment
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.screen.tests.library.LibraryFragment
import com.testeducation.ui.screen.tests.liked.LikedTestsFragment
import com.testeducation.ui.screen.tests.list.TestsFragment
import com.testeducation.ui.screen.tests.preview.TestPreviewFragment
import com.testeducation.ui.utils.withScreen

class ScreenAdapter : IScreenAdapter {

    override fun createPlatformScreen(screen: NavigationScreen): Screen =
        when (screen) {
            is NavigationScreen.Auth -> createPlatformScreen(screen)
            is NavigationScreen.Common -> createPlatformScreen(screen)
            is NavigationScreen.Main -> createPlatformScreen(screen)
            is NavigationScreen.Tests -> createPlatformScreen(screen)
            is NavigationScreen.QuestionCreation -> createPlatformScreen(screen)
        }

    private fun createPlatformScreen(screen: NavigationScreen.Auth): Screen =
        when (screen) {

            NavigationScreen.Auth.Login -> Screen.FragmentScreen {
                LoginFragment()
            }

            NavigationScreen.Auth.Registration -> Screen.FragmentScreen {
                RegistrationFragment()
            }

            is NavigationScreen.Auth.CodeConfirmation -> Screen.FragmentScreen {
                CodeConfirmationFragment().withScreen(screen)
            }

            NavigationScreen.Auth.PasswordResetEmail -> Screen.FragmentScreen {
                PasswordResetEmailFragment()
            }

            is NavigationScreen.Auth.NewPassword -> Screen.FragmentScreen {
                NewPasswordFragment().withScreen(screen)
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

            is NavigationScreen.Common.PopUpInformation -> Screen.DialogScreen {
                InformationAlertDialog().withScreen(screen)
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

        is NavigationScreen.Main.SelectionTest -> Screen.DialogScreen {
            SelectionQuestionTypeDialog().withScreen(screen)
        }

        NavigationScreen.Main.LikedTests -> Screen.FragmentScreen {
            LikedTestsFragment()
        }

        NavigationScreen.Main.Library -> Screen.FragmentScreen {
            LibraryFragment()
        }
    }

    private fun createPlatformScreen(screen: NavigationScreen.Tests) = when (screen) {

        is NavigationScreen.Tests.Filters -> Screen.FragmentScreen {
            TestsFiltersFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.Library -> Screen.FragmentScreen {
            TestLibraryFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.Details -> Screen.FragmentScreen {
            TestEditorFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.Preview -> Screen.FragmentScreen {
            TestPreviewFragment().withScreen(screen)
        }
    }

    private fun createPlatformScreen(screen: NavigationScreen.QuestionCreation) = when(screen) {
        is NavigationScreen.QuestionCreation.QuestionEditor -> Screen.FragmentScreen {
            QuestionCreationFragment().withScreen(screen)
        }
    }
}

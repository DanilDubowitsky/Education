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
import com.testeducation.ui.screen.profile.ProfileAvatarChangerFragment
import com.testeducation.ui.screen.profile.ProfileEditFragment
import com.testeducation.ui.screen.profile.ProfileFragment
import com.testeducation.ui.screen.profile.SupportSenderFragment
import com.testeducation.ui.screen.questions.QuestionsPreviewDialog
import com.testeducation.ui.screen.tests.code.TestCodeShareDialog
import com.testeducation.ui.screen.tests.creation.CreationTestDialogFragment
import com.testeducation.ui.screen.tests.creation.QuestionCreationFragment
import com.testeducation.ui.screen.tests.creation.SelectionQuestionTypeDialog
import com.testeducation.ui.screen.tests.creation.input.AnswerInputDialog
import com.testeducation.ui.screen.tests.creation.time.TimeQuestionDialog
import com.testeducation.ui.screen.tests.edit.TestEditorFragment
import com.testeducation.ui.screen.tests.edit.TestSettingsFragment
import com.testeducation.ui.screen.tests.edit.TestStyleChangerFragment
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.screen.tests.library.LibraryFragment
import com.testeducation.ui.screen.tests.liked.LikedTestsFragment
import com.testeducation.ui.screen.tests.list.TestsFragment
import com.testeducation.ui.screen.tests.pass.TestPassingFragment
import com.testeducation.ui.screen.tests.pass.result.TestFailedPassDialog
import com.testeducation.ui.screen.tests.pass.result.TestPassResultDialog
import com.testeducation.ui.screen.tests.preview.TestPreviewFragment
import com.testeducation.ui.screen.tests.statistic.TestPassStatisticFragment
import com.testeducation.ui.screen.webview.WebViewFragment
import com.testeducation.ui.utils.withScreen

class ScreenAdapter : IScreenAdapter {

    override fun createPlatformScreen(screen: NavigationScreen): Screen =
        when (screen) {
            is NavigationScreen.Auth -> createPlatformScreen(screen)
            is NavigationScreen.Common -> createPlatformScreen(screen)
            is NavigationScreen.Main -> createPlatformScreen(screen)
            is NavigationScreen.Tests -> createPlatformScreen(screen)
            is NavigationScreen.Questions -> createPlatformScreen(screen)
            is NavigationScreen.Profile -> createPlatformScreen(screen)
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

            is NavigationScreen.Common.WebView -> Screen.FragmentScreen {
                WebViewFragment().withScreen(screen)
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

        NavigationScreen.Main.Profile -> Screen.FragmentScreen {
            ProfileFragment()
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

        is NavigationScreen.Tests.Settings -> Screen.FragmentScreen {
            TestSettingsFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.TestStyleChangerData -> Screen.FragmentScreen {
            TestStyleChangerFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.Passing -> Screen.FragmentScreen {
            TestPassingFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.Result -> Screen.DialogScreen {
            TestPassResultDialog().withScreen(screen)
        }

        is NavigationScreen.Tests.Statistic -> Screen.FragmentScreen {
            TestPassStatisticFragment().withScreen(screen)
        }

        is NavigationScreen.Tests.FailedResult -> Screen.DialogScreen {
            TestFailedPassDialog().withScreen(screen)
        }

        is NavigationScreen.Tests.ShareCode -> Screen.DialogScreen {
            TestCodeShareDialog().withScreen(screen)
        }
    }

    private fun createPlatformScreen(screen: NavigationScreen.Questions) = when(screen) {
        is NavigationScreen.Questions.QuestionEditor -> Screen.FragmentScreen {
            QuestionCreationFragment().withScreen(screen)
        }
        is NavigationScreen.Questions.TimeQuestion -> Screen.DialogScreen {
            TimeQuestionDialog().withScreen(screen)
        }

        is NavigationScreen.Questions.QuestionsPreview -> Screen.DialogScreen {
            QuestionsPreviewDialog().withScreen(screen)
        }

        is NavigationScreen.Questions.AnswerInput -> Screen.DialogScreen {
            AnswerInputDialog().withScreen(screen)
        }
    }

    private fun createPlatformScreen(screen: NavigationScreen.Profile) = when(screen) {
        NavigationScreen.Profile.Editor -> Screen.FragmentScreen {
            ProfileEditFragment().withScreen(screen)
        }
        is NavigationScreen.Profile.Avatar -> Screen.FragmentScreen {
            ProfileAvatarChangerFragment().withScreen(screen)
        }
        NavigationScreen.Profile.Support -> Screen.FragmentScreen {
            SupportSenderFragment().withScreen(screen)
        }
    }

}

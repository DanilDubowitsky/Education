package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.screen.auth.CodeConfirmationModule
import com.testeducation.education.di.modules.screen.auth.LoginModule
import com.testeducation.education.di.modules.screen.auth.NewPasswordModule
import com.testeducation.education.di.modules.screen.auth.PasswordResetEmailModule
import com.testeducation.education.di.modules.screen.auth.RegistrationModule
import com.testeducation.education.di.modules.screen.home.HomeModule
import com.testeducation.education.di.modules.screen.home.library.HomeLibraryModule
import com.testeducation.education.di.modules.screen.home.library.LibraryModule
import com.testeducation.education.di.modules.screen.profile.AboutAppModule
import com.testeducation.education.di.modules.screen.profile.ProfileAvatarChangerModule
import com.testeducation.education.di.modules.screen.profile.ProfileEditModule
import com.testeducation.education.di.modules.screen.profile.ProfileModule
import com.testeducation.education.di.modules.screen.profile.SupportSenderModule
import com.testeducation.education.di.modules.screen.tests.edit.TestEditorModule
import com.testeducation.education.di.modules.screen.tests.edit.TestStyleChangerModule
import com.testeducation.education.di.modules.screen.tests.filters.TestsFiltersModule
import com.testeducation.education.di.modules.screen.tests.library.TestLibraryModule
import com.testeducation.education.di.modules.screen.tests.liked.LikedTestsModule
import com.testeducation.education.di.modules.screen.tests.list.TestsModule
import com.testeducation.education.di.modules.screen.tests.pass.TestPassingModule
import com.testeducation.education.di.modules.screen.tests.preview.TestPreviewModule
import com.testeducation.education.di.modules.screen.tests.question.QuestionCreationModule
import com.testeducation.education.di.modules.screen.tests.settings.TestSettingsModule
import com.testeducation.education.di.modules.screen.tests.statistic.TestPassStatisticModule
import com.testeducation.education.di.modules.screen.webview.WebViewModule
import com.testeducation.ui.screen.auth.confirmation.CodeConfirmationFragment
import com.testeducation.ui.screen.auth.login.LoginFragment
import com.testeducation.ui.screen.auth.registration.RegistrationFragment
import com.testeducation.ui.screen.auth.reset.email.PasswordResetEmailFragment
import com.testeducation.ui.screen.auth.reset.password.NewPasswordFragment
import com.testeducation.ui.screen.home.FragmentHome
import com.testeducation.ui.screen.home.library.LibraryHomeFragment
import com.testeducation.ui.screen.profile.AboutAppFragment
import com.testeducation.ui.screen.profile.ProfileAvatarChangerFragment
import com.testeducation.ui.screen.profile.ProfileEditFragment
import com.testeducation.ui.screen.profile.ProfileFragment
import com.testeducation.ui.screen.profile.SupportSenderFragment
import com.testeducation.ui.screen.tests.creation.QuestionCreationFragment
import com.testeducation.ui.screen.tests.edit.TestEditorFragment
import com.testeducation.ui.screen.tests.edit.TestSettingsFragment
import com.testeducation.ui.screen.tests.edit.TestStyleChangerFragment
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.screen.tests.library.LibraryFragment
import com.testeducation.ui.screen.tests.library.test.TestLibraryFragment
import com.testeducation.ui.screen.tests.liked.LikedTestsFragment
import com.testeducation.ui.screen.tests.list.TestsFragment
import com.testeducation.ui.screen.tests.pass.TestPassingFragment
import com.testeducation.ui.screen.tests.preview.TestPreviewFragment
import com.testeducation.ui.screen.tests.statistic.TestPassStatisticFragment
import com.testeducation.ui.screen.webview.WebViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector(modules = [RegistrationModule::class])
    fun registrationFragment(): RegistrationFragment

    @ContributesAndroidInjector(modules = [CodeConfirmationModule::class])
    fun emailConfirmationFragment(): CodeConfirmationFragment

    @ContributesAndroidInjector(modules = [LoginModule::class])
    fun loginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    fun homeFragment(): FragmentHome

    @ContributesAndroidInjector(modules = [TestsModule::class])
    fun testsFragment(): TestsFragment

    @ContributesAndroidInjector(modules = [TestsFiltersModule::class])
    fun testsFiltersFragment(): TestsFiltersFragment

    @ContributesAndroidInjector(modules = [LikedTestsModule::class])
    fun likedTestsFragment(): LikedTestsFragment

    @ContributesAndroidInjector(modules = [QuestionCreationModule::class])
    fun questionCreationFragment(): QuestionCreationFragment

    @ContributesAndroidInjector(modules = [PasswordResetEmailModule::class])
    fun passwordResetEmailFragment(): PasswordResetEmailFragment

    @ContributesAndroidInjector(modules = [NewPasswordModule::class])
    fun newPasswordFragment(): NewPasswordFragment

    @ContributesAndroidInjector(modules = [TestEditorModule::class])
    fun testEditorFragment(): TestEditorFragment

    @ContributesAndroidInjector(modules = [TestLibraryModule::class])
    fun testLibraryFragment(): TestLibraryFragment

    @ContributesAndroidInjector(modules = [LibraryModule::class])
    fun libraryFragment(): LibraryFragment

    @ContributesAndroidInjector(modules = [TestPreviewModule::class])
    fun testPreviewFragment(): TestPreviewFragment

    @ContributesAndroidInjector(modules = [TestSettingsModule::class])
    fun testSettingsFragment(): TestSettingsFragment

    @ContributesAndroidInjector(modules = [TestStyleChangerModule::class])
    fun testStyleChangerFragment(): TestStyleChangerFragment

    @ContributesAndroidInjector(modules = [TestPassingModule::class])
    fun testPassingFragment(): TestPassingFragment

    @ContributesAndroidInjector(modules = [TestPassStatisticModule::class])
    fun testPassStatisticFragment(): TestPassStatisticFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    fun profileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [ProfileEditModule::class])
    fun profileEditFragment(): ProfileEditFragment

    @ContributesAndroidInjector(modules = [ProfileAvatarChangerModule::class])
    fun profileAvatarChangerFragment(): ProfileAvatarChangerFragment

    @ContributesAndroidInjector(modules = [SupportSenderModule::class])
    fun supportSenderFragment(): SupportSenderFragment

    @ContributesAndroidInjector(modules = [WebViewModule::class])
    fun webViewFragment(): WebViewFragment

    @ContributesAndroidInjector(modules = [HomeLibraryModule::class])
    fun homeLibraryFragment(): LibraryHomeFragment

    @ContributesAndroidInjector(modules = [AboutAppModule::class])
    fun aboutAppFragment(): AboutAppFragment

}

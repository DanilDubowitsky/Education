package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.screen.auth.CodeConfirmationModule
import com.testeducation.education.di.modules.screen.auth.LoginModule
import com.testeducation.education.di.modules.screen.auth.NewPasswordModule
import com.testeducation.education.di.modules.screen.auth.PasswordResetEmailModule
import com.testeducation.education.di.modules.screen.auth.RegistrationModule
import com.testeducation.education.di.modules.screen.home.HomeModule
import com.testeducation.education.di.modules.screen.tests.edit.TestEditorModule
import com.testeducation.education.di.modules.screen.tests.filters.TestsFiltersModule
import com.testeducation.education.di.modules.screen.tests.liked.LikedTestsModule
import com.testeducation.education.di.modules.screen.tests.list.TestsModule
import com.testeducation.education.di.modules.screen.tests.question.QuestionCreationModule
import com.testeducation.ui.screen.auth.confirmation.CodeConfirmationFragment
import com.testeducation.ui.screen.auth.login.LoginFragment
import com.testeducation.ui.screen.auth.registration.RegistrationFragment
import com.testeducation.ui.screen.auth.reset.email.PasswordResetEmailFragment
import com.testeducation.ui.screen.auth.reset.password.NewPasswordFragment
import com.testeducation.ui.screen.home.FragmentHome
import com.testeducation.ui.screen.tests.creation.QuestionCreationFragment
import com.testeducation.ui.screen.tests.edit.TestEditorFragment
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.screen.tests.liked.LikedTestsFragment
import com.testeducation.ui.screen.tests.list.TestsFragment
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
    fun testEditorFragment() : TestEditorFragment

}

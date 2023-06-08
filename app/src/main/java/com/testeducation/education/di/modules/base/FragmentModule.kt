package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.fragment.auth.EmailConfirmationModule
import com.testeducation.education.di.modules.fragment.auth.LoginModule
import com.testeducation.education.di.modules.fragment.auth.RegistrationModule
import com.testeducation.education.di.modules.fragment.home.HomeModule
import com.testeducation.education.di.modules.fragment.tests.filters.TestsFiltersModule
import com.testeducation.education.di.modules.fragment.tests.list.TestsModule
import com.testeducation.ui.screen.auth.confirmation.EmailConfirmationFragment
import com.testeducation.ui.screen.auth.login.LoginFragment
import com.testeducation.ui.screen.auth.registration.RegistrationFragment
import com.testeducation.ui.screen.home.FragmentHome
import com.testeducation.ui.screen.tests.filters.TestsFiltersFragment
import com.testeducation.ui.screen.tests.list.TestsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector(modules = [RegistrationModule::class])
    fun registrationFragment(): RegistrationFragment

    @ContributesAndroidInjector(modules = [EmailConfirmationModule::class])
    fun emailConfirmationFragment(): EmailConfirmationFragment

    @ContributesAndroidInjector(modules = [LoginModule::class])
    fun loginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    fun homeFragment(): FragmentHome

    @ContributesAndroidInjector(modules = [TestsModule::class])
    fun testsFragment(): TestsFragment

    @ContributesAndroidInjector(modules = [TestsFiltersModule::class])
    fun testsFiltersFragment(): TestsFiltersFragment

}

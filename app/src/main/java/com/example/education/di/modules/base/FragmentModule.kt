package com.example.education.di.modules.base

import com.example.education.di.modules.fragment.auth.EmailConfirmationModule
import com.example.education.di.modules.fragment.auth.LoginModule
import com.example.education.di.modules.fragment.auth.RegistrationModule
import com.example.education.di.modules.fragment.home.HomeModule
import com.example.ui.screen.auth.confirmation.EmailConfirmationFragment
import com.example.ui.screen.auth.login.LoginFragment
import com.example.ui.screen.auth.registration.RegistrationFragment
import com.example.ui.screen.home.FragmentHome
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

}

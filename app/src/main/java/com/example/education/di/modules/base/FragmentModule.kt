package com.example.education.di.modules.base

import com.example.education.di.modules.fragment.registration.EmailConfirmationModule
import com.example.education.di.modules.fragment.registration.RegistrationModule
import com.example.ui.screen.auth.confirmation.EmailConfirmationFragment
import com.example.ui.screen.auth.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {

    @ContributesAndroidInjector(modules = [RegistrationModule::class])
    fun registrationFragment(): RegistrationFragment

    @ContributesAndroidInjector(modules = [EmailConfirmationModule::class])
    fun emailConfirmationFragment(): EmailConfirmationFragment

}

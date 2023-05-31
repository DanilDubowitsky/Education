package com.testeducation.education.di.modules.base

import com.example.education.di.modules.fragment.home.tests.creation.TestCreationModule
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.example.ui.screen.tests.creation.CreationTestDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DialogsModule {

    @ContributesAndroidInjector
    fun confirmationDialog(): ConfirmationDialog

    @ContributesAndroidInjector
    fun informationDialog(): InformationDialog

    @ContributesAndroidInjector(modules = [TestCreationModule::class])
    fun creationTestDialog() : CreationTestDialogFragment
}
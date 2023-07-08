package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.fragment.home.tests.creation.TestCreationModule
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationAlertDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.testeducation.ui.screen.tests.creation.CreationTestDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DialogsModule {

    @ContributesAndroidInjector
    fun confirmationDialog(): ConfirmationDialog

    @ContributesAndroidInjector
    fun informationDialog(): InformationDialog

    @ContributesAndroidInjector
    fun informationAlertDialog(): InformationAlertDialog

    @ContributesAndroidInjector(modules = [TestCreationModule::class])
    fun creationTestDialog() : CreationTestDialogFragment
}
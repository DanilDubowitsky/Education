package com.example.education.di.modules.base

import com.example.ui.screen.common.ConfirmationDialog
import com.example.ui.screen.common.InformationDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DialogsModule {

    @ContributesAndroidInjector
    fun confirmationDialog(): ConfirmationDialog

    @ContributesAndroidInjector
    fun informationDialog(): InformationDialog
}
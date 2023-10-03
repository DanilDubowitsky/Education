package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.screen.tests.creation.TestCreationModule
import com.testeducation.education.di.modules.screen.tests.question.QuestionModule
import com.testeducation.education.di.modules.screen.tests.question.time.TimeQuestionModule
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationAlertDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.testeducation.ui.screen.tests.creation.CreationTestDialogFragment
import com.testeducation.ui.screen.tests.creation.SelectionQuestionTypeDialog
import com.testeducation.ui.screen.tests.creation.time.TimeQuestionDialog
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
    fun creationTestDialog(): CreationTestDialogFragment

    @ContributesAndroidInjector(modules = [QuestionModule::class])
    fun selectionQuestionTypeDialog(): SelectionQuestionTypeDialog

    @ContributesAndroidInjector(modules = [TimeQuestionModule::class])
    fun timeQuestionDialog(): TimeQuestionDialog
}
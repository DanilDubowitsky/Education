package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.screen.question.QuestionsPreviewModule
import com.testeducation.education.di.modules.screen.tests.code.enter.TestCodeEnterModule
import com.testeducation.education.di.modules.screen.tests.code.share.TestCodeShareModule
import com.testeducation.education.di.modules.screen.tests.creation.TestCreationModule
import com.testeducation.education.di.modules.screen.tests.pass.FullAnswerTextModule
import com.testeducation.education.di.modules.screen.tests.pass.TestFailedPassModule
import com.testeducation.education.di.modules.screen.tests.pass.TestPassResultModule
import com.testeducation.education.di.modules.screen.tests.question.QuestionModule
import com.testeducation.education.di.modules.screen.tests.question.input.AnswerInputModule
import com.testeducation.education.di.modules.screen.tests.question.time.TimeQuestionModule
import com.testeducation.ui.screen.common.ConfirmationDialog
import com.testeducation.ui.screen.common.InformationAlertDialog
import com.testeducation.ui.screen.common.InformationDialog
import com.testeducation.ui.screen.questions.QuestionsPreviewDialog
import com.testeducation.ui.screen.tests.code.TestCodeEnterDialog
import com.testeducation.ui.screen.tests.code.TestCodeShareDialog
import com.testeducation.ui.screen.tests.creation.CreationTestDialogFragment
import com.testeducation.ui.screen.tests.creation.SelectionQuestionTypeDialog
import com.testeducation.ui.screen.tests.creation.input.AnswerInputDialog
import com.testeducation.ui.screen.tests.creation.time.TimeQuestionDialog
import com.testeducation.ui.screen.tests.pass.answer.FullAnswerTextDialog
import com.testeducation.ui.screen.tests.pass.result.TestFailedPassDialog
import com.testeducation.ui.screen.tests.pass.result.TestPassResultDialog
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

    @ContributesAndroidInjector(modules = [QuestionsPreviewModule::class])
    fun questionsPreviewDialog(): QuestionsPreviewDialog

    @ContributesAndroidInjector(modules = [AnswerInputModule::class])
    fun answerInputDialog(): AnswerInputDialog

    @ContributesAndroidInjector(modules = [TestPassResultModule::class])
    fun testResultDialog(): TestPassResultDialog

    @ContributesAndroidInjector(modules = [TestFailedPassModule::class])
    fun testFailedPassDialog(): TestFailedPassDialog

    @ContributesAndroidInjector(modules = [TestCodeShareModule::class])
    fun testCodeShareDialog(): TestCodeShareDialog

    @ContributesAndroidInjector(modules = [TestCodeEnterModule::class])
    fun testCodeEnterDialog(): TestCodeEnterDialog

    @ContributesAndroidInjector(modules = [FullAnswerTextModule::class])
    fun testFullAnswerDialog(): FullAnswerTextDialog
}

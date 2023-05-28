package com.testeducation.education.di.modules.base

import com.testeducation.education.di.modules.activity.MainActivityModule
import com.testeducation.education.di.modules.viewmodel.ViewModelFactoryModule
import com.testeducation.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            ViewModelFactoryModule::class,
            FragmentModule::class,
            DialogsModule::class
        ]
    )
    fun mainActivity(): MainActivity

}

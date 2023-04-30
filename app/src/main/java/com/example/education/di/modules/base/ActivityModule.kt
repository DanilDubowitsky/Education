package com.example.education.di.modules.base

import com.example.ui.activity.MainActivity
import com.example.education.di.modules.activity.MainActivityModule
import com.example.education.di.modules.viewmodel.ViewModelFactoryModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            ViewModelFactoryModule::class,
            FragmentModule::class
        ]
    )
    fun mainActivity(): MainActivity

}

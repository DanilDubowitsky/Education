package com.example.education.di.modules.helper

import android.content.Context
import com.example.helper.resource.IResourceHelper
import com.example.ui.helper.resource.ResourceHelper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ResourceHelperModule {

    @Provides
    @Reusable
    fun provideResourceHelper(context: Context): IResourceHelper = ResourceHelper(context)
}

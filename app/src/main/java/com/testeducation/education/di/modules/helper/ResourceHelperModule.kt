package com.testeducation.education.di.modules.helper

import android.content.Context
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.ui.helper.resource.ResourceHelper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object ResourceHelperModule {

    @Provides
    @Reusable
    fun provideResourceHelper(context: Context): IResourceHelper = ResourceHelper(context)
}

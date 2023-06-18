package com.testeducation.education.di.modules.helper

import android.content.Context
import com.testeducation.core.app.IAppBuildConfigHelper
import com.testeducation.education.version.AppBuildConfigHelper
import com.testeducation.helper.resource.IResourceHelper
import com.testeducation.ui.helper.resource.ResourceHelper
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object HelperModule {

    @Provides
    @Reusable
    fun provideResourceHelper(context: Context): IResourceHelper = ResourceHelper(context)

    @Provides
    @Reusable
    fun provideAppBuildConfigHelper(): IAppBuildConfigHelper = AppBuildConfigHelper()

}

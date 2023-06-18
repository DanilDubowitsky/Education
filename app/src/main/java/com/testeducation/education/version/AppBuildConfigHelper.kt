package com.testeducation.education.version

import com.testeducation.core.app.IAppBuildConfigHelper
import com.testeducation.education.BuildConfig

class AppBuildConfigHelper : IAppBuildConfigHelper {

    override fun getAppVersion(): String = BuildConfig.VERSION_NAME

}
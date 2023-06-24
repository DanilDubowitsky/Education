package com.testeducation.core.config.internal

import com.testeducation.core.app.IAppBuildConfigHelper
import com.testeducation.core.config.IConfigSource
import com.testeducation.core.config.updateAndRead
import com.testeducation.core.source.remote.internal.IAppVersionRemoteSource
import com.testeducation.domain.config.internal.IAppVersionConfig

class AppVersionConfig(
    private val configSource: IConfigSource,
    private val appUpdateRemoteSource: IAppVersionRemoteSource,
    private val buildConfigHelper: IAppBuildConfigHelper
) : IAppVersionConfig {

    override suspend fun isUpdateRequire(): Boolean = configSource.updateAndRead(
        ::updateIsUpdateRequire
    ) {
        configSource.getBoolean(IS_UPDATE_REQUIRED_KEY)
    }

    private suspend fun updateIsUpdateRequire() {
        val appVersion = appUpdateRemoteSource.getActualAppVersion()
        val isUpdateRequired = checkAppUpdateAvailable(appVersion.minVersion)
        configSource.setBoolean(IS_UPDATE_REQUIRED_KEY, isUpdateRequired)
    }

    private fun checkAppUpdateAvailable(minAppVersion: String): Boolean {
        val currentVersionNumber = parseAppVersionToNumber(buildConfigHelper.getAppVersion())
        val minVersionNumber = parseAppVersionToNumber(minAppVersion)
        return currentVersionNumber < minVersionNumber
    }

    private fun parseAppVersionToNumber(version: String): Int {
        var appVersionNumber = 0
        version.forEach { char ->
            if (char.isDigit()) {
                appVersionNumber += char.digitToInt()
            }
        }
        return appVersionNumber
    }

    companion object {
        const val APP_VERSION_CONFIG_NAME = "APP_VERSION_CONFIG"
        private const val IS_UPDATE_REQUIRED_KEY = "IS_UPDATE_REQUIRED"
    }
}
